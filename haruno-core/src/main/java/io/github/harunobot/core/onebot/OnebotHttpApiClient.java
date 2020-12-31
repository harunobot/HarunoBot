/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.util.BotResponseUtils;
import io.github.harunobot.proto.request.type.RequestType;
import io.github.harunobot.proto.response.BotResponse;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import java.util.Map;
import org.slf4j.LoggerFactory;
import io.github.harunobot.async.BotResponseCallback;
import io.github.harunobot.proto.event.BotEvent;
import io.vertx.core.http.HttpVersion;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotHttpApiClient<T> implements AutoCloseable {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotHttpApiClient.class);
    
    private final String authorization;
    private final String host;
    private final int port;
    private final String uri;
    private final Vertx vertx;
    private final WebClient client;
    
    private OnebotHttpApiClient(Builder builder){
        this.authorization = builder.authorization;
        this.host = builder.host;
        this.port = builder.port;
        this.uri = builder.uri;
        this.vertx = builder.vertx;
        WebClientOptions options = new WebClientOptions()
                .setUserAgent("HarunoBot/0.1.0")
//                .setProtocolVersion(HttpVersion.HTTP_2)
//                .setSsl(true)
//                .setUseAlpn(true)
//                .setTrustAll(true)
                .setKeepAlive(true)
                .setTcpKeepAlive(true)
                ;
        this.client = WebClient.create(this.vertx, options);
    }
    
    public static class Builder {
        private String authorization;
        private String host;
        private int port;
        private String uri;
        private Vertx vertx;
        
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
        
        public Builder vertx(Vertx vertx){
            this.vertx = vertx;
            return this;
        }
        
        public OnebotHttpApiClient build(){
            return new OnebotHttpApiClient(this);
        }
    }
    
    public void fetchGetMsg(Map<String, Object> params, BotResponseCallback<T> callback){
        HttpRequest<Buffer> request = client.post(
                        this.port
                        , this.host
                        , this.uri + "/get_msg")
                .bearerTokenAuthentication(this.authorization)
                .putHeader("Accept", "application/json") 
                .expect(ResponsePredicate.SC_OK)
                ;
        request.sendJson(params, ar ->{
            if (!ar.succeeded()) {
                LOG.error("", ar.cause());
                return;
            }
            ar.result().bodyAsBuffer();
            BotResponse<T> botResponse
                    = BotResponseUtils.convertMessage(
                            RequestType.GET_MESSAGE
                            , ar.result().bodyAsJson(OnebotApiResponse.class)
                    );
            callback.handle(botResponse);
        });
    }

    @Override
    public void close() throws Exception {
        this.client.close();
    }
    
}
