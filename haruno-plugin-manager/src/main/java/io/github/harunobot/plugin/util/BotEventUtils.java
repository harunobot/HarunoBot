/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.util;

import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import io.github.harunobot.core.proto.onebot.event.type.ArrayMessageDataType;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.event.BotMessage;
import io.github.harunobot.proto.event.type.EventType;
import io.github.harunobot.proto.event.type.MessageContentType;
import io.github.harunobot.proto.event.type.SenderType;
import io.github.harunobot.proto.event.type.SourceType;
import java.util.HashMap;

/**
 *
 * @author iTeam_VEP
 * 
 */
public class BotEventUtils {
    
    public static BotEvent convertMessage(OnebotEvent event){
        BotEvent.Builder builder = new BotEvent.Builder();
        builder.eventType(EventType.MESSAGE);
        builder.botId(event.getSelfId());
        builder.userId(event.getUserId());
        builder.messageId(event.getMessageId());
        
//        ONLY MESSAGE Event can be here
//        switch(event.getPostType()){
//            case MESSAGE:
//                break;
//            case NOTICE:
//                break;
//            case REQUEST:
//                break;
//            case META:
//                break;
//            default:
//                break;
//        }
        if(event.getMessageType() != null){
            switch(event.getMessageType()){
                case PRIVATE:
                    builder.sourceType(SourceType.PRIVATE);
                    break;
                case GROUP:
                    builder.sourceType(SourceType.PUBLIC);
                    builder.groupId(event.getGroupId());
                    break;
                default:
                    break;
            }
        }
        if(event.getSubType() != null){
            switch(event.getSubType()){
                case FRIEND:
                    builder.senderType(SenderType.FRIEND);
                    break;
                case GROUP:
                    builder.senderType(SenderType.PUBLIC);
                    break;
                case OTHER:
                    builder.senderType(SenderType.OTHER);
                    break;
                case NORMAL:
                    builder.senderType(SenderType.NORMAL);
                    break;
                case ANONYMOUS:
                    builder.senderType(SenderType.ANONYMOUS);
                    break;
                case NOTICE:
                    builder.senderType(SenderType.NOTICE);
                    break;
                case SET:
                    break;
                case UNSET:
                    break;
                case LEAVE:
                    break;
                case KICK:
                    break;
                case KICK_ME:
                    break;
                case APPROVE:
                    break;
                case INVITE:
                    break;
                case BAN:
                    break;
                case BAN_RESCISED:
                    break;
                case POKE:
                    break;
                case LUCKY_KING:
                    break;
                case HONOR:
                    break;
                case ENABLE:
                    break;
                case DISABLE:
                    break;
                case CONNECT:
                    break;
                default:
                    break;
            }
        }
        if(event.getNoticeType() != null){
            switch(event.getNoticeType()){
                case GROUP_UPLOAD:
                    break;
                case GROUP_ADMIN:
                    break;
                case GROUP_DECREASE:
                    break;
                case GROUP_INCREASE:
                    break;
                case GROUP_BAN:
                    break;
                case GROUP_RECALL:
                    break;
                case FRIEND_ADD:
                    break;
                case FRIEND_RECALL:
                    break;
                case NOTIFY:
                    break;
                default:
                    break;
            }
        }
        if(event.getRequestType() != null){
            switch(event.getRequestType()){
                case FRIEND:
                    break;
                case GROUP:
                    break;
                default:
                    break;
            }
        }
        builder.rawMessage(event.getRawMessage());
//        builder.sender(sender)
        ArrayMessage[] messages = (ArrayMessage[]) event.getMessage();
        BotMessage[] botMessages = new BotMessage[messages.length];
        for(int i=0; i< messages.length; i++){
            BotMessage.Builder messageBuilder = new BotMessage.Builder();
            switch(messages[i].getType()){
                case TEXT:
                    messageBuilder.messageType(MessageContentType.TEXT);
                    messageBuilder.data((String) messages[i].getData().get("text"));
                    break;
                case FACE:
                    messageBuilder.messageType(MessageContentType.MEME);
                    messageBuilder.data((String) messages[i].getData().get("id"));
                    break;
                case IMAGE:
                    messageBuilder.messageType(MessageContentType.IMAGE);
                    if("flash".equals(messages[i].getData().get("type"))){
//                        builder.destruct(true);
                        builder.duration(1);
                    }
                    messageBuilder.file((String) messages[i].getData().get("file"));
                    messageBuilder.url((String) messages[i].getData().get("url"));
                    break;
                case RECORD:
                    messageBuilder.file((String) messages[i].getData().get("file"));
                    messageBuilder.url((String) messages[i].getData().get("url"));
                    messageBuilder.magic(Boolean.valueOf((String) messages[i].getData().get("magic")));
                    break;
                case VIDEO:
                    break;
                case AT:
                    messageBuilder.messageType(MessageContentType.MENTION);
                    messageBuilder.data((String) messages[i].getData().get("qq"));
                    break;
                case RPS:
                    break;
                case DICE:
                    break;
                case SHAKE:
                    break;
                case POKE:
                    break;
                case ANONYMOUS:
                    break;
                case SHARE:
                    break;
                case CONTACT:
                    break;
                case LOCATION:
                    break;
                case REPLY:
                    messageBuilder.messageType(MessageContentType.REPLY);
                    messageBuilder.data((String) messages[i].getData().get("id"));
                    break;
                case FORWARD:
                    break;
                case NODE:
                    break;
                case XML:
                    break;
                case JSON:
                    break;
                default:
                    break;
            }
            botMessages[i] = messageBuilder.build();
        }
        builder.messages(botMessages);
        return builder.build();
    }
    
    public static ArrayMessage[] convertMessage(BotMessage[] messages, boolean destruct){
        ArrayMessage[] arrayMessage = new ArrayMessage[messages.length];
        for(int i=0; i< messages.length; i++){
            ArrayMessage message = new ArrayMessage();
            message.setData(new HashMap());
            switch(messages[i].messageType()){
                case TEXT:
                    message.setType(ArrayMessageDataType.TEXT);
                    message.getData().put("text", messages[i].data());
                    break;
                case MEME:
                    message.setType(ArrayMessageDataType.FACE);
                    message.getData().put("id", messages[i].data());
                    break;
                case IMAGE:
                    message.setType(ArrayMessageDataType.IMAGE);
                    if(destruct){
                        message.getData().put("type", "flash");
                    }
                    message.getData().put("file", messages[i].file());
                    message.getData().put("url", messages[i].url());
                    break;
                case AUDIO:
                    message.setType(ArrayMessageDataType.RECORD);
                    message.getData().put("file", messages[i].file());
                    message.getData().put("magic", messages[i].magic()?1:0);
                    break;
                case VIDEO:
                    break;
                case MENTION:
                    message.setType(ArrayMessageDataType.AT);
                    message.getData().put("qq", messages[i].data());
                    break;
                case RPS:
                    break;
                case DICE:
                    break;
                case SHAKE:
                    break;
                case POKE:
                    break;
                case ANONYMOUS:
                    break;
                case SHARE:
                    break;
                case CONTACT:
                    break;
                case LOCATION:
                    break;
                case REPLY:
                    message.setType(ArrayMessageDataType.REPLY);
                    message.getData().put("id", messages[i].data());
                    break;
                case FORWARD:
                    break;
                case NODE:
                    break;
                case XML:
                    break;
                case JSON:
                    break;
                default:
                    break;
            }
            arrayMessage[i] = message;
        }
        return arrayMessage;
    }
    
    public BotMessage[] messageConverter(ArrayMessage[] messages){
        return null;
    }
    
}
