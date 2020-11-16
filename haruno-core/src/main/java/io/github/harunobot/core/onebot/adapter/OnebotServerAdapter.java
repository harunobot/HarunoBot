/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.adapter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;
import java.util.Random;
import io.github.harunobot.core.onebot.OnebotReceptionManager;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotServerAdapter extends AbstractVerticle  {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotServerAdapter.class);
    
    private final String authorization;
    private final String host;
    private final int port;
    private final String uri;
    private final OnebotReceptionManager handler;
    
    private OnebotServerAdapter(Builder builder){
        this.authorization = builder.authorization;
        this.host = builder.host;
        this.port = builder.port;
        this.uri = builder.uri;
        this.handler = builder.handler;
    }
    
    public static class Builder {
        private String authorization;
        private String host;
        private int port;
        private String uri;
        private OnebotReceptionManager handler;
        
        public Builder handler(OnebotReceptionManager handler){
            this.handler = handler;
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
        
        public OnebotServerAdapter build(){
            return new OnebotServerAdapter(this);
        }
    }
    
    @Override
    public void start() {
        startServer(vertx);
    }

    private void startServer(Vertx vertx) {
        HttpServer server = vertx.createHttpServer();
        server
                .webSocketHandler((ServerWebSocket ctx) -> routeWebSocket(ctx))
                .requestHandler((HttpServerRequest ctx) -> routeHttpRequest(ctx))
                .listen(port);
    }
    
    private void routeHttpRequest(HttpServerRequest ctx){
        switch(ctx.path()){
            case "/ws/":
                break;
            default:
                
        }
    }
    
    private void routeWebSocket(ServerWebSocket ctx){
        switch(ctx.path()){
            case "/ws/onebot":
                handleOnebotWebsocket(ctx);
                break;
            case "/ws/test":
                handleTestWebsocket(ctx);
                break;
            default:
                handleDefaultWebsocket(ctx);
        }
    }
    
    private void handleOnebotWebsocket(ServerWebSocket ctx){
        
    }
    
    private void handleTestWebsocket(ServerWebSocket ctx){
        ctx.writeTextMessage("ping");
        
        ctx.textMessageHandler((msg) -> {
            System.out.println("Server " + msg);

            if ((new Random()).nextInt(100) == 0) {
                ctx.close();
            } else {
                ctx.writeTextMessage("ping");
            }
        });
    }
    
    private void handleDefaultWebsocket(ServerWebSocket ctx){
        ctx.writeTextMessage("error path");
        ctx.close();
    }

    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise); //To change body of generated methods, choose Tools | Templates.
    }
    
}
