/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventTranslatorOneArg;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.type.MessageType;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotEventHandler implements com.lmax.disruptor.EventHandler<OnebotEvent>, EventTranslatorOneArg<OnebotEvent, JsonNode> {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotEventHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OnebotProtoHandler protoHandler;
    
    public OnebotEventHandler(OnebotProtoHandler protoHandler){
        this.protoHandler = protoHandler;
    }
    
    @Override
    public void translateTo(OnebotEvent event, long sequence, JsonNode json)
    {
        try {
            objectMapper.readerForUpdating(event).readValue(json);
        } catch (JsonProcessingException ex) {
            MDC.put("module", "OnebotEventHandler");
            LOG.error("translate failed: {}", json.toString(), ex);
            MDC.clear();
        } catch (IOException ex) {
            MDC.put("module", "OnebotEventHandler");
            LOG.error("translate failed: {}", json.toString(), ex);
            MDC.clear();
        }
    }
    
    /**
     *
     * @param event OnebotEvent
     * @param sequence event sequence
     * @param endOfBatch end Of Batch
     */
    @Override
    public void onEvent(OnebotEvent event, long sequence, boolean endOfBatch)
    {
        if(event.getPostType() == null) {
            try {
                MDC.put("module", "OnebotEventHandler");
                LOG.warn("handleEvent sequence: {} endOfBatch: {} {}", sequence, endOfBatch, objectMapper.writeValueAsString(event));
                MDC.clear();
                return;
            } catch (JsonProcessingException ex) {
                
            }
        }
        switch(event.getPostType()){
            case META:
                protoHandler.handleMetaEventMessage(event);
                break;
            case NOTICE:
                protoHandler.handleNoticeMessage(event);
                break;
            case REQUEST:
                protoHandler.handleRequestMessage(event);
                break;
            case MESSAGE:
                if(event.getMessageType() == MessageType.PRIVATE){
                    protoHandler.handlePrivateMessage(event);
                    break;
                }
                if(event.getMessageType() == MessageType.GROUP){
                    protoHandler.handleGroupMessage(event);
                    break;
                }
            default:
                protoHandler.handleDefaultMessage(event);
                break;
        }
    }
    
}
