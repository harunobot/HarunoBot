/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import io.github.harunobot.core.onebot.config.OnebotConnectProperties;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.vertx.core.Vertx;
import io.github.harunobot.exception.HarunoIllegalArgumentException;
import io.github.harunobot.core.onebot.adapter.OnebotClientAdapter;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotControlCenter {
    private Disruptor<OnebotEvent> eventDisruptor;
    private Disruptor<OnebotApiResponse> apiResponseDisruptor;
    private Disruptor<StringBuilder> apiDisruptor;
    private OnebotEventHandler eventHandler;
    private OnebotApiResponseHandler apiResponseHandler;
    private OnebotApiHandler apiHandler;
    private OnebotReceptionManager receptionManager;
    private OnebotTransmissionManager transmissionManager;
    private OnebotClientAdapter clientAdapter;
//    private OnebotHttpApiClient httpApiClient;
    private Vertx vertx;
    
    private final OnebotProtoHandler protoHandler;
    private final OnebotConnectProperties connectProperties;
    
    public OnebotControlCenter(OnebotProtoHandler protoHandler, OnebotConnectProperties connectProperties){
        this.protoHandler = protoHandler;
        this.connectProperties = connectProperties;
    }
    
    public void start() throws HarunoIllegalArgumentException{
        
        eventHandler = new OnebotEventHandler(protoHandler);
        apiResponseHandler = new OnebotApiResponseHandler(protoHandler);
        apiHandler = new OnebotApiHandler();
        
        int bufferSize = 1024;
        eventDisruptor = new Disruptor(OnebotEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);
        eventDisruptor.handleEventsWith(eventHandler);
        eventDisruptor.start();
        
        apiResponseDisruptor = new Disruptor(OnebotApiResponse::new, bufferSize, DaemonThreadFactory.INSTANCE);
        apiResponseDisruptor.handleEventsWith(apiResponseHandler);
        apiResponseDisruptor.start();
        
        apiDisruptor = new Disruptor(StringBuilder::new, bufferSize, DaemonThreadFactory.INSTANCE);
        apiDisruptor.handleEventsWith(apiHandler);
        apiDisruptor.start();
        
        
        vertx = Vertx.vertx();
//        eventDisruptor.getRingBuffer(), apiResponseDisruptor.getRingBuffer()
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
        protoHandler.setTransmitter(transmissionManager);
        clientAdapter 
                = new OnebotClientAdapter.Builder()
                        .authorization(connectProperties.getAuthorization())
                        .host(connectProperties.getWsHost())
                        .port(connectProperties.getWsPort())
                        .uri(connectProperties.getWsUri())
                        .receptionManager(receptionManager)
                        .transmissionManager(transmissionManager)
                        .build();
        vertx.deployVerticle(clientAdapter);
//        try {
//            Thread.sleep(5 * 1000);
//        } catch (InterruptedException ex) {
//        }
//        OnebotApi sendGroupMsg = new ApiGenerator.SendGroupMsg().groupId(121249285).message("test").build();
//        String json = new ObjectMapper().writeValueAsString(sendGroupMsg);
//        transmissionManager.transmit(json);
//        OnebotServerAdapter serverAdapter = new OnebotServerAdapter.Builder().port(8080).build();
//        vertx.deployVerticle(serverAdapter, (__) -> {
//            vertx.deployVerticle(clientAdapter);
//        });
    }
    
}
