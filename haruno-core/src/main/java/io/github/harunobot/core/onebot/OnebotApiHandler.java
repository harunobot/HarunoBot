/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventTranslatorOneArg;
import io.github.harunobot.core.proto.onebot.api.OnebotApi;
import org.slf4j.LoggerFactory;
import io.vertx.core.http.WebSocketBase;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotApiHandler implements com.lmax.disruptor.EventHandler<StringBuilder>, EventTranslatorOneArg<StringBuilder, OnebotApi> {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotApiHandler.class);
    private final ObjectMapper objectMapper;
    
    private WebSocketBase ctx;
    
    public OnebotApiHandler(){
        this.objectMapper = new ObjectMapper();
    }
    
    @Override
    public void translateTo(StringBuilder sb, long sequence, OnebotApi api)
    {
        sb.setLength(0);
        try {
            sb.append(objectMapper.writeValueAsString(api));;
        } catch (JsonProcessingException ex) {
            MDC.put("module", "OnebotApiHandler");
            LOG.error("translate failed", ex);
            MDC.clear();
        }
    }

    @Override
    public void onEvent(StringBuilder sb, long sequence, boolean endOfBatch) throws Exception {
        MDC.put("module", "OnebotApiHandler");
        try{
            ctx.writeTextMessage(sb.toString());
        }catch(Exception ex){
            LOG.error("", ex);
        }
        MDC.clear();
    }
    
    public final void setCtx(WebSocketBase ctx){
        this.ctx = ctx;
    }
    
}
