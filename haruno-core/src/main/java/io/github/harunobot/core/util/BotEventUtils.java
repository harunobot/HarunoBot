/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.util;

import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import io.github.harunobot.core.proto.onebot.event.type.ArrayMessageDataType;
import io.github.harunobot.exception.HarunoIllegalArgumentException;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.event.BotMessage;
import io.github.harunobot.proto.event.type.EventType;
import io.github.harunobot.proto.event.type.MessageContentType;
import io.github.harunobot.proto.event.type.DirectiveType;
import io.github.harunobot.proto.event.type.SourceType;
import io.github.harunobot.pojo.type.Permission;
import java.util.HashMap;

/**
 *
 * @author iTeam_VEP
 * 
 */
public class BotEventUtils {
    
    public static BotEvent convertMessage(OnebotEvent event){
        if(event == null){
            return null;
        }
//        if(event.getNoticeType() != null){
//            return convertOnebotNotice(event);
//        }
//        if(event.getRequestType() != null){
//            return convertOnebotRequest(event);
//        }
//        if(event.getMessageType() != null){
//            return convertOnebotMessage(event);
//        }
//        if(event.getPostType() == null){
//            return convertOnebotMessage(event);
//        }
        switch(event.getPostType()){
            case MESSAGE:{
                return convertOnebotMessage(event);
            }
            case NOTICE:{
                return convertOnebotNotice(event);
            }
            case REQUEST:{
                return convertOnebotRequest(event);
            }
        }
        throw new HarunoIllegalArgumentException("Onebot event type unknown");
    }
    
    public static ArrayMessage[] convertMessage(BotMessage[] messages){
        if(messages == null){
            return null;
        }
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
                    if(messages[i].destruct()){
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
    
    private static BotEvent convertOnebotRequest(OnebotEvent event){
        BotEvent.Builder builder = new BotEvent.Builder();
        builder.eventType(EventType.MESSAGE);
        builder.botId(event.getSelfId());
        if(event.getUserId() ==0 && event.getSender().getUserId() !=0){
            builder.userId(event.getSender().getUserId());
        } else {
            builder.userId(event.getUserId());
        }
        builder.groupId(event.getGroupId());
        builder.operatorId(event.getOperatorId());
        builder.messageId(event.getMessageId());
        builder.timestamp(event.getTime());
        builder.comment(event.getComment());
        builder.flag(event.getFlag());
        boolean privSource = false;
        switch(event.getRequestType()){
            case FRIEND:
                builder.sourceType(SourceType.PRIVATE);
                builder.permission(Permission.RECEIVE_PRIVATE_REQUEST);
                builder.directiveType(DirectiveType.PRIVATE_ADD_REQUEST);
                privSource = true;
                break;
            case GROUP:
                builder.sourceType(SourceType.PUBLIC);
                builder.permission(Permission.RECEIVE_PUBLIC_REQUEST);
                builder.groupId(event.getGroupId());
                break;
            default:
                break;
        }
        if(event.getSubType() != null){
            switch(event.getSubType()){
                case ADD:
                    if(!privSource){
                        builder.directiveType(DirectiveType.PUBLIC_ADD_REQUEST);
                    }
                    break;
                case INVITE:
                    if(!privSource){
                        builder.directiveType(DirectiveType.PUBLIC_INVITE_REQUEST);
                    }
                    break;
                default:
                    break;
            }
        }
        builder.rawMessage(event.getRawMessage());
        return builder.build();
    }
    
    private static BotEvent convertOnebotNotice(OnebotEvent event){
        BotEvent.Builder builder = new BotEvent.Builder();
        builder.eventType(EventType.MESSAGE);
        builder.botId(event.getSelfId());
        if(event.getUserId() ==0 && event.getSender().getUserId() !=0){
            builder.userId(event.getSender().getUserId());
        } else {
            builder.userId(event.getUserId());
        }
        builder.groupId(event.getGroupId());
        builder.operatorId(event.getOperatorId());
        builder.messageId(event.getMessageId());
        builder.timestamp(event.getTime());
        boolean privSource = false;
        switch(event.getNoticeType()){
            case GROUP_UPLOAD:
                break;
            case GROUP_ADMIN:
                break;
            case GROUP_DECREASE:
                builder.sourceType(SourceType.PUBLIC);
            //    builder.directiveType(DirectiveType.PUBLIC_DECREASE);
                builder.permission(Permission.PUBLIC_DECREASE_NOTICE);
                break;
            case GROUP_INCREASE:
                builder.sourceType(SourceType.PUBLIC);
            //    builder.directiveType(DirectiveType.PUBLIC_INCREASE);
                builder.permission(Permission.PUBLIC_INCREASE_NOTICE);
                break;
            case GROUP_BAN:
                break;
            case GROUP_RECALL:
                builder.permission(Permission.PUBLIC_DELETE_NOTICE);
                break;
            case FRIEND_ADD:
                privSource = true;
                break;
            case FRIEND_RECALL:
                builder.permission(Permission.PRIVATE_DELETE_NOTICE);
                privSource = true;
                break;
            case NOTIFY:
                break;
            default:
                break;
        }
        if(event.getSubType() != null){
            switch(event.getSubType()){
                case SET:
                    break;
                case UNSET:
                    break;
                case LEAVE:
                    builder.directiveType(DirectiveType.PUBLIC_LEAVE_NOTICE);
                    break;
                case KICKED:
                    builder.directiveType(DirectiveType.KICKED_NOTICE);
                    break;
                case KICKED_ME:
                    builder.directiveType(DirectiveType.KICKED_ME_NOTICE);
                    break;
                case APPROVED:
                    if(!privSource){
                        builder.directiveType(DirectiveType.PUBLIC_APPROVED_NOTICE);
                    }
                    break;
                case INVITE:
                    if(!privSource){
                        builder.directiveType(DirectiveType.PUBLIC_INVITE_NOTICE);
                    }
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
                default:
                    break;
            }
        }
        builder.sourceType(privSource?SourceType.PRIVATE:SourceType.PUBLIC);
        builder.rawMessage(event.getRawMessage());
        return builder.build();
    }
    
    private static BotEvent convertOnebotMessage(OnebotEvent event){
        BotEvent.Builder builder = new BotEvent.Builder();
        builder.eventType(EventType.MESSAGE);
        builder.botId(event.getSelfId());
        if(event.getUserId() ==0 && event.getSender().getUserId() !=0){
            builder.userId(event.getSender().getUserId());
        } else {
            builder.userId(event.getUserId());
        }
        builder.messageId(event.getMessageId());
        builder.timestamp(event.getTime());
        boolean privSource = false;
        switch(event.getMessageType()){
        case PRIVATE:
            builder.sourceType(SourceType.PRIVATE);
            builder.permission(Permission.RECEIVE_PRIVATE_MESSAGE);
            privSource = true;
            break;
        case GROUP:
            builder.sourceType(SourceType.PUBLIC);
            builder.permission(Permission.RECEIVE_PUBLIC_MESSAGE);
            builder.groupId(event.getGroupId());
            break;
        default:
            break;
        }
        if(event.getSubType() != null){
            switch(event.getSubType()){
                case FRIEND:
                    builder.directiveType(DirectiveType.FRIEND);
                    break;
                case GROUP:
                    builder.directiveType(DirectiveType.PUBLIC);
                    break;
                case OTHER:
                    builder.directiveType(DirectiveType.OTHER);
                    break;
                case NORMAL:
                    builder.directiveType(DirectiveType.NORMAL);
                    break;
                case ANONYMOUS:
                    builder.directiveType(DirectiveType.ANONYMOUS);
                    break;
                case NOTICE:
                    builder.directiveType(DirectiveType.NOTICE);
                    break;
                default:
                    break;
            }
        }
        builder.rawMessage(event.getRawMessage());
//        builder.sender(sender)
        ArrayMessage[] messages = (ArrayMessage[]) event.getMessage();
        if(messages == null) {
            return builder.build();
        }
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
                        messageBuilder.destruct(true);
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
    
    private static Permission botEventPermission(BotEvent botEvent){
        if(botEvent.sourceType() == SourceType.PRIVATE){
            switch(botEvent.directiveType()){
                
            }
        }
        if(botEvent.sourceType() == SourceType.PUBLIC){
            switch(botEvent.directiveType()){
                
            }
        }
        throw new HarunoIllegalArgumentException("Can't infer the permission");
    }
    
    public BotMessage[] messageConverter(ArrayMessage[] messages){
        return null;
    }
    
//    public static BotEvent convertMessage(OnebotEvent event){
//        BotEvent.Builder builder = new BotEvent.Builder();
//        builder.eventType(EventType.MESSAGE);
//        builder.botId(event.getSelfId());
//        if(event.getUserId() ==0 && event.getSender().getUserId() !=0){
//            builder.userId(event.getSender().getUserId());
//        } else {
//            builder.userId(event.getUserId());
//        }
//        builder.messageId(event.getMessageId());
//        builder.timestamp(event.getTime());
////        ONLY MESSAGE Event can be here
////        switch(event.getPostType()){
////            case MESSAGE:
////                break;
////            case NOTICE:
////                break;
////            case REQUEST:
////                break;
////            case META:
////                break;
////            default:
////                break;
////        }
//        if(event.getMessageType() != null){
//            switch(event.getMessageType()){
//                case PRIVATE:
//                    builder.sourceType(SourceType.PRIVATE);
//                    break;
//                case GROUP:
//                    builder.sourceType(SourceType.PUBLIC);
//                    builder.groupId(event.getGroupId());
//                    break;
//                default:
//                    break;
//            }
//        }
//        if(event.getSubType() != null){
//            switch(event.getSubType()){
//                case FRIEND:
//                    builder.directiveType(DirectiveType.FRIEND);
//                    break;
//                case GROUP:
//                    builder.directiveType(DirectiveType.PUBLIC);
//                    break;
//                case OTHER:
//                    builder.directiveType(DirectiveType.OTHER);
//                    break;
//                case NORMAL:
//                    builder.directiveType(DirectiveType.NORMAL);
//                    break;
//                case ANONYMOUS:
//                    builder.directiveType(DirectiveType.ANONYMOUS);
//                    break;
//                case NOTICE:
//                    builder.directiveType(DirectiveType.NOTICE);
//                    break;
//                case SET:
//                    break;
//                case UNSET:
//                    break;
//                case LEAVE:
//                    break;
//                case KICK:
//                    break;
//                case KICK_ME:
//                    break;
//                case APPROVE:
//                    break;
//                case ADD:
//                    builder.directiveType(DirectiveType.PUBLIC_ADD);
//                    break;
//                case INVITE:
//                    builder.directiveType(DirectiveType.PUBLIC_INVITE);
//                    break;
//                case BAN:
//                    break;
//                case BAN_RESCISED:
//                    break;
//                case POKE:
//                    break;
//                case LUCKY_KING:
//                    break;
//                case HONOR:
//                    break;
//                case ENABLE:
//                    break;
//                case DISABLE:
//                    break;
//                case CONNECT:
//                    break;
//                default:
//                    break;
//            }
//        }
//        if(event.getNoticeType() != null){
//            switch(event.getNoticeType()){
//                case GROUP_UPLOAD:
//                    break;
//                case GROUP_ADMIN:
//                    break;
//                case GROUP_DECREASE:
//                    break;
//                case GROUP_INCREASE:
//                    break;
//                case GROUP_BAN:
//                    break;
//                case GROUP_RECALL:
//                    break;
//                case FRIEND_ADD:
//                    break;
//                case FRIEND_RECALL:
//                    break;
//                case NOTIFY:
//                    break;
//                default:
//                    break;
//            }
//        }
//        if(event.getRequestType() != null){
//            switch(event.getRequestType()){
//                case FRIEND:
//                    break;
//                case GROUP:
//                    break;
//                default:
//                    break;
//            }
//        }
//        builder.rawMessage(event.getRawMessage());
////        builder.sender(sender)
//        ArrayMessage[] messages = (ArrayMessage[]) event.getMessage();
//        if(messages == null) {
//            return builder.build();
//        }
//        BotMessage[] botMessages = new BotMessage[messages.length];
//        for(int i=0; i< messages.length; i++){
//            BotMessage.Builder messageBuilder = new BotMessage.Builder();
//            switch(messages[i].getType()){
//                case TEXT:
//                    messageBuilder.messageType(MessageContentType.TEXT);
//                    messageBuilder.data((String) messages[i].getData().get("text"));
//                    break;
//                case FACE:
//                    messageBuilder.messageType(MessageContentType.MEME);
//                    messageBuilder.data((String) messages[i].getData().get("id"));
//                    break;
//                case IMAGE:
//                    messageBuilder.messageType(MessageContentType.IMAGE);
//                    if("flash".equals(messages[i].getData().get("type"))){
////                        builder.destruct(true);
//                        builder.duration(1);
//                        messageBuilder.destruct(true);
//                    }
//                    messageBuilder.file((String) messages[i].getData().get("file"));
//                    messageBuilder.url((String) messages[i].getData().get("url"));
//                    break;
//                case RECORD:
//                    messageBuilder.file((String) messages[i].getData().get("file"));
//                    messageBuilder.url((String) messages[i].getData().get("url"));
//                    messageBuilder.magic(Boolean.valueOf((String) messages[i].getData().get("magic")));
//                    break;
//                case VIDEO:
//                    break;
//                case AT:
//                    messageBuilder.messageType(MessageContentType.MENTION);
//                    messageBuilder.data((String) messages[i].getData().get("qq"));
//                    break;
//                case RPS:
//                    break;
//                case DICE:
//                    break;
//                case SHAKE:
//                    break;
//                case POKE:
//                    break;
//                case ANONYMOUS:
//                    break;
//                case SHARE:
//                    break;
//                case CONTACT:
//                    break;
//                case LOCATION:
//                    break;
//                case REPLY:
//                    messageBuilder.messageType(MessageContentType.REPLY);
//                    messageBuilder.data((String) messages[i].getData().get("id"));
//                    break;
//                case FORWARD:
//                    break;
//                case NODE:
//                    break;
//                case XML:
//                    break;
//                case JSON:
//                    break;
//                default:
//                    break;
//            }
//            botMessages[i] = messageBuilder.build();
//        }
//        builder.messages(botMessages);
//        return builder.build();
//    }
    
}
