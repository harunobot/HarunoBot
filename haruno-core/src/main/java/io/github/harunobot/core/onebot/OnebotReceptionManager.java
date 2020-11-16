/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.RingBuffer;
import io.github.harunobot.core.onebot.base.OnebotReceiver;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotReceptionManager implements OnebotReceiver {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotReceptionManager.class);

    private final RingBuffer<OnebotEvent> eventRingBuffer;
    private final RingBuffer<OnebotApiResponse> apiResponseBuffer;
    private final OnebotEventHandler eventHandler;
    private final OnebotApiResponseHandler apiResponseHandler;
    private final ObjectMapper objectMapper;
    
    public OnebotReceptionManager(Builder builder){
        this.eventRingBuffer = builder.eventRingBuffer;
        this.apiResponseBuffer = builder.apiResponseBuffer;
        this.eventHandler = builder.eventHandler;
        this.apiResponseHandler = builder.apiResponseHandler;
        this.objectMapper = new ObjectMapper();
    }

    
    @Override
    public void receive(String json)
    {
        try {
            JsonNode parent = objectMapper.readTree(json);
            if(parent.has("retcode")){
                apiResponseBuffer.publishEvent(apiResponseHandler, parent);
            } else if(parent.has("post_type")){
                eventRingBuffer.publishEvent(eventHandler, parent);
            }
        } catch (JsonProcessingException ex) {
            MDC.put("module", "OnebotReceptionManager");
            LOG.error("handle receive failed", ex);
            MDC.clear();
        }
    }

    public static class Builder {
        private RingBuffer<OnebotEvent> eventRingBuffer;
        private RingBuffer<OnebotApiResponse> apiResponseBuffer;
        private OnebotEventHandler eventHandler;
        private OnebotApiResponseHandler apiResponseHandler;
        private OnebotProtoHandler pluginManger;
        
        public Builder eventRingBuffer(RingBuffer<OnebotEvent> eventRingBuffer){
            this.eventRingBuffer = eventRingBuffer;
            return this;
        }
        
        public Builder apiResponseBuffer(RingBuffer<OnebotApiResponse> apiResponseBuffer){
            this.apiResponseBuffer = apiResponseBuffer;
            return this;
        }
        
        public Builder eventHandler(OnebotEventHandler eventHandler){
            this.eventHandler = eventHandler;
            return this;
        }
        
        public Builder apiResponseHandler(OnebotApiResponseHandler apiResponseHandler){
            this.apiResponseHandler = apiResponseHandler;
            return this;
        }
        
        public Builder pluginManger(OnebotProtoHandler pluginManger){
            this.pluginManger = pluginManger;
            return this;
        }
        
        public OnebotReceptionManager build(){
            return new OnebotReceptionManager(this);
        }
    }
    
}
