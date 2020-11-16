/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import com.lmax.disruptor.RingBuffer;
import io.vertx.core.http.WebSocketBase;
import io.github.harunobot.core.onebot.base.OnebotTransmitter;
import io.github.harunobot.core.proto.onebot.api.OnebotApi;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotTransmissionManager implements OnebotTransmitter {
    private final RingBuffer<StringBuilder> apiBuffer;
    private final OnebotApiHandler apiHandler;
    
    public OnebotTransmissionManager(Builder builder){
        this.apiBuffer = builder.apiBuffer;
        this.apiHandler = builder.apiHandler;
    }

    public static class Builder {
        private RingBuffer<StringBuilder> apiBuffer;
        private OnebotApiHandler apiHandler;
        
        public Builder apiBuffer(RingBuffer<StringBuilder> apiBuffer){
            this.apiBuffer = apiBuffer;
            return this;
        }
        
        public Builder apiHandler(OnebotApiHandler apiHandler){
            this.apiHandler = apiHandler;
            return this;
        }
        
        public OnebotTransmissionManager build(){
            return new OnebotTransmissionManager(this);
        }
    }
    
    @Override
    public void transmit(OnebotApi api)
    {
        MDC.put("module", "OnebotTransmissionManager");
        apiBuffer.publishEvent(apiHandler, api);
        MDC.clear();
    }
    
    public void setCtx(WebSocketBase ctx) {
        apiHandler.setCtx(ctx);
    }
}
