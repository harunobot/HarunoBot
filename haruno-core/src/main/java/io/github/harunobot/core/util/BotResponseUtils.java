/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.api.type.Status;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.type.MessageType;
import io.github.harunobot.proto.request.type.RequestType;
import io.github.harunobot.proto.response.BotResponse;
import io.github.harunobot.proto.response.type.StatusType;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class BotResponseUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(BotResponseUtils.class);
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static BotResponse convertMessage(RequestType requestType, OnebotApiResponse apiResponse){
        if(apiResponse == null){
            return null;
        }
        BotResponse botResponse = new BotResponse();
        if(apiResponse.getStatus() == Status.FAILED){
            botResponse.setStatus(StatusType.FAILED);
        } else {
            botResponse.setStatus(StatusType.SUCCESS);
        }
        switch(requestType){
            case GET_MESSAGE:{
                try {
                    OnebotEvent event = objectMapper.treeToValue(apiResponse.getData(), OnebotEvent.class);
                    if(event.isGroup()){
                        event.setMessageType(MessageType.GROUP);
                    } else {
                        event.setMessageType(MessageType.PRIVATE);
                    }
//                    BotEvent.Builder builder = new BotEvent.Builder();
//                    builder.messageId(event.getMessageId());
//                    if(event.isGroup()){
//                        builder.sourceType(SourceType.PUBLIC);
//                    } else {
//                        builder.sourceType(SourceType.PRIVATE);
//                    }
//                    event.getUserId();
//                    builder.groupId(event.getGroupId());
//                    builder.userId(event.getSender().getUserId());
//                    builder.realId(event.getRealId());
//                    builder.rawMessage(event.getRawMessage());
//                    builder.messages((BotMessage[]) event.getMessage());
//                    builder.timestamp(event.getTime());
//                    botResponse.setData(builder.build());
                    botResponse.setData(BotEventUtils.convertMessage(event));
                } catch (JsonProcessingException ex) {
                    LOG.error("", ex);
                }
                break;
            }
        }
        return botResponse;
    }
    
}
