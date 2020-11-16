/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.adapter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.WebSocket;
import io.vertx.core.http.WebSocketConnectOptions;
import java.util.concurrent.TimeUnit;
import io.github.harunobot.core.onebot.OnebotReceptionManager;
import io.github.harunobot.core.onebot.OnebotTransmissionManager;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 * https://richardchien.gitee.io/coolq-http-api/docs/4.15/#/CommunicationMethods
 * https://richardchien.gitee.io/coolq-http-api/docs/4.15/#/WebSocketAPI
 * https://github.com/howmanybots/onebot/blob/master/v11/specs/event/README.md
 */
public class OnebotClientAdapter extends AbstractVerticle {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotClientAdapter.class);
    private final String authorization;
    private final String host;
    private final int port;
    private final String uri;
    private final OnebotReceptionManager receptionManager;
    private final OnebotTransmissionManager transmissionManager;
    
    private OnebotClientAdapter(Builder builder){
        this.authorization = builder.authorization;
        this.host = builder.host;
        this.port = builder.port;
        this.uri = builder.uri;
        this.receptionManager = builder.receptionManager;
        this.transmissionManager = builder.transmissionManager;
    }
    
    public static class Builder {
        private String authorization;
        private String host;
        private int port;
        private String uri;
        private OnebotReceptionManager receptionManager;
        private OnebotTransmissionManager transmissionManager;
        
        public Builder receptionManager(OnebotReceptionManager receptionManager){
            this.receptionManager = receptionManager;
            return this;
        }
        
        public Builder transmissionManager(OnebotTransmissionManager transmissionManager){
            this.transmissionManager = transmissionManager;
            return this;
        }
        
        public Builder authorization(String authorization){
            this.authorization = authorization;
            return this;
        }
        
        public Builder host(String host){
            this.host = host;
            return this;
        }
        
        public Builder port(int port){
            this.port = port;
            return this;
        }
        
        public Builder uri(String uri){
            this.uri = uri;
            return this;
        }
        
        public OnebotClientAdapter build(){
            return new OnebotClientAdapter(this);
        }
    }
    
    @Override
    public void start() {
        startClient(vertx);
    }
    
    private void startClient(Vertx vertx) {
        MDC.put("module", "OnebotClientAdapter");
        HttpClient client = vertx.createHttpClient();
        MultiMap headers = MultiMap.caseInsensitiveMultiMap();
        headers.add("Authorization", "Bearer "+authorization);
        
        WebSocketConnectOptions connectOptions = new WebSocketConnectOptions();
        connectOptions.setHeaders(headers);
        connectOptions.setHost(host);
        connectOptions.setPort(port);
        connectOptions.setURI(uri);
        client.webSocket(connectOptions, (ar) -> {
            if (ar.succeeded()) {
                WebSocket ctx = ar.result();
                transmissionManager.setCtx(ctx);
                ctx.textMessageHandler((msg) -> {
//                    System.out.println("Client " + msg);
                    receptionManager.receive(msg);
//                    ctx.writeTextMessage("pong");
                }).exceptionHandler((e) -> {
                    MDC.put("module", "OnebotClientAdapter");
//                    System.out.println("Error, restarting in 5 seconds");
                    LOG.error("Error, restarting in 5 seconds", e);
                    restart(client, 5);
                    MDC.clear();
                }).closeHandler((__) -> {
                    MDC.put("module", "OnebotClientAdapter");
//                    System.out.println("Closed, restarting in 10 seconds");
                    LOG.error("Closed, restarting in 10 seconds");
                    restart(client, 10);
                    MDC.clear();
                });
            } else {
                MDC.put("module", "OnebotClientAdapter");
                LOG.error("Connect failed", ar.cause());
                MDC.clear();
                restart(client, 30);
            }
        });
        MDC.clear();
    }
    
    private void restart(HttpClient client, int delay) {
        client.close();
        vertx.setTimer(TimeUnit.SECONDS.toMillis(delay), (__) -> {
            startClient(vertx);
        });
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise); //To change body of generated methods, choose Tools | Templates.
    }
    
}
