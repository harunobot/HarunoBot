/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.console;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.github.harunobot.plugin.onebot.PluginManager;
import io.github.harunobot.core.onebot.config.OnebotConnectProperties;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.harunobot.console.configuration.BotProperties;
import io.github.harunobot.core.onebot.OnebotApiHandler;
import io.github.harunobot.core.onebot.OnebotApiResponseHandler;
import io.github.harunobot.core.onebot.OnebotEventHandler;
import io.github.harunobot.core.onebot.OnebotHttpApiClient;
import io.github.harunobot.core.onebot.OnebotReceptionManager;
import io.github.harunobot.core.onebot.OnebotTransmissionManager;
import io.github.harunobot.core.onebot.adapter.OnebotClientAdapter;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class BotVerticle extends AbstractVerticle {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(BotVerticle.class);
    
    private Disruptor<OnebotEvent> eventDisruptor;
    private Disruptor<OnebotApiResponse> apiResponseDisruptor;
    private Disruptor<StringBuilder> apiDisruptor;
    private OnebotEventHandler eventHandler;
    private OnebotApiResponseHandler apiResponseHandler;
    private OnebotApiHandler apiHandler;
    private OnebotReceptionManager receptionManager;
    private OnebotTransmissionManager transmissionManager;
    private OnebotClientAdapter clientAdapter;
    private PluginManager pluginManger;
    private String workFolder;
    private BotProperties properties;
    private Vertx pluginVertx;
    
//    public static void main(String[] args){
//        
//    }
    
    @Override
    public void start() {
        MDC.put("module", "Laucher");
        try {
            environment();
            configure();
            vertx.deployVerticle(clientAdapter);
            plugin();
        } catch (IOException ex) {
            LOG.error("", ex);
            vertx.close();
            System.exit(-1);
        }
        MDC.put("module", "Laucher");
        LOG.info("Harunobot startup...");
//        MDC.clear();
    }
    
    @Override
    public void stop() throws Exception{
        MDC.put("module", "Laucher");
        pluginManger.unloadPlugins();
        eventDisruptor.shutdown();
        apiResponseDisruptor.shutdown();
        apiDisruptor.shutdown();
        pluginManger.close();
    }
    
    private void plugin(){
        if(properties.getPlugin() == null){
            return;
        }
        if(properties.getPlugin().getPluginFolder() != null){
            for(String folder:properties.getPlugin().getPluginFolder()){
                pluginManger.loadPlugins(new File(folder));
            }
        }
        pluginManger.activePlugins();
    }
    
    private void configure(){
        OnebotConnectProperties connectProperties = properties.getOnebotConnection();
        
        OnebotHttpApiClient httpApiClient
                = new OnebotHttpApiClient.Builder()
                        .vertx(vertx)
                        .authorization(connectProperties.getAuthorization())
                        .host(connectProperties.getHttpHost())
                        .port(connectProperties.getHttpPort())
                        .uri(connectProperties.getHttpUri())
                        .build(); 
        VertxOptions pluginOptions = new VertxOptions();
        pluginOptions.setWorkerPoolSize(50);
        pluginOptions.setBlockedThreadCheckInterval(5);
        pluginOptions.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);
        pluginOptions.setMaxEventLoopExecuteTime(500);
        pluginOptions.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);
        pluginOptions.setMaxWorkerExecuteTime(10);
        pluginOptions.setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);
        pluginOptions.setWarningExceptionTime(30);
        pluginOptions.setWarningExceptionTimeUnit(TimeUnit.SECONDS);
        pluginVertx = Vertx.vertx(pluginOptions);
        
        pluginManger = new PluginManager(new ArrayList(), vertx, httpApiClient, pluginVertx);
//        pluginManger.loadPlugins(new File("L:\\NetBeans\\NetBeansProjects\\Haruno-Admin-Plugin\\build\\libs"));
//        pluginManger.loadPlugins(new File("L:\\NetBeans\\NetBeansProjects\\HarunoPlugins\\Kancolle-Chart-Assistant-Plugin\\build\\libs"));
        
        eventHandler = new OnebotEventHandler(pluginManger);
        apiResponseHandler = new OnebotApiResponseHandler(pluginManger);
        apiHandler = new OnebotApiHandler();
        
        int bufferSize = 128;
        eventDisruptor = new Disruptor(OnebotEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        eventDisruptor.handleEventsWith(eventHandler);
        eventDisruptor.start();
        
        apiResponseDisruptor = new Disruptor(OnebotApiResponse::new, bufferSize, DaemonThreadFactory.INSTANCE);
        apiResponseDisruptor.handleEventsWith(apiResponseHandler);
        apiResponseDisruptor.start();
        
        apiDisruptor = new Disruptor(StringBuilder::new, bufferSize, DaemonThreadFactory.INSTANCE);
        apiDisruptor.handleEventsWith(apiHandler);
        apiDisruptor.start();

        receptionManager 
                = new OnebotReceptionManager.Builder()
                        .eventRingBuffer(eventDisruptor.getRingBuffer())
                        .apiResponseBuffer(apiResponseDisruptor.getRingBuffer())
                        .eventHandler(eventHandler)
                        .apiResponseHandler(apiResponseHandler)
//                        .pluginManger(pluginManger)
                        .build();
        transmissionManager 
                = new OnebotTransmissionManager.Builder()
                        .apiBuffer(apiDisruptor.getRingBuffer())
                        .apiHandler(apiHandler)
                        .build();
        pluginManger.setTransmitter(transmissionManager);
        clientAdapter 
                = new OnebotClientAdapter.Builder()
                        .authorization(connectProperties.getAuthorization())
                        .host(connectProperties.getWsHost())
                        .port(connectProperties.getWsPort())
                        .uri(connectProperties.getWsUri())
                        .receptionManager(receptionManager)
                        .transmissionManager(transmissionManager)
                        .build();
    }
    
    private void environment() throws IOException{
        workFolder = FileSystems.getDefault().getPath("").toAbsolutePath().toString();
        LOG.info("workFolder: {}", workFolder);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).enable(SerializationFeature.INDENT_OUTPUT);
        properties = JsonUtils.jsonToObject(
                Files.readString(
                        FileSystems.getDefault().getPath("config.yml")
                        , StandardCharsets.UTF_8)
                , BotProperties.class
                ,mapper);
    }
    
}
