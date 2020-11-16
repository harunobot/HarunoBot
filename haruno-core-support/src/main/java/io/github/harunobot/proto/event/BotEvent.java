/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.proto.event;

import io.github.harunobot.proto.event.type.EventType;
import io.github.harunobot.proto.event.type.SourceType;
import io.github.harunobot.proto.event.type.SenderType;

/**
 *
 * @author iTeam_VEP
 */
public class BotEvent {
    private final EventType eventType;
    private final SourceType sourceType;
    private final SenderType senderType;
    private final BotMessage[] messages;
    private final String rawMessage;
    private final Sender sender;
    private final long botId;
    private final long groupId;
    private final long channelId;
    private final long userId;
    private final long messageId;
    private final long operatorId;
    private final long targetId;
    private final long realId;
    private final String comment;
//    private final boolean destruct;
    private final long duration;
    private final Object extendData;
    
    public BotEvent(Builder builder){
        this.eventType = builder.eventType;
        this.sourceType = builder.sourceType;
        this.senderType = builder.senderType;
        this.messages = builder.messages;
        this.rawMessage = builder.rawMessage;
        this.sender = builder.sender;
        this.botId = builder.botId;
        this.groupId = builder.groupId;
        this.channelId = builder.channelId;
        this.userId = builder.userId;
        this.messageId = builder.messageId;
        this.operatorId = builder.operatorId;
        this.targetId = builder.targetId;
        this.realId = builder.realId;
        this.comment = builder.comment;
        this.extendData = builder.extendData;
//        this.destruct = builder.destruct;
        this.duration = builder.duration;
    }
    
    public static class Builder {
        private EventType eventType;
        private SourceType sourceType;
        private SenderType senderType;
        private BotMessage[] messages;
        private String rawMessage;
        private Sender sender;
        private long botId;
        private long groupId;
        private long channelId;
        private long userId;
        private long messageId;
        private long operatorId;
        private long targetId;
        private long realId;
        private String comment;
//        private boolean destruct;
        private long duration;
        private Object extendData;
        
        public Builder eventType(EventType eventType){
            this.eventType = eventType;
            return this;
        }
        
        public Builder sourceType(SourceType sourceType){
            this.sourceType = sourceType;
            return this;
        }
        
        public Builder senderType(SenderType senderType){
            this.senderType = senderType;
            return this;
        }
        
        public Builder messages(BotMessage[] messages){
            this.messages = messages;
            return this;
        }
        
        public Builder rawMessage(String rawMessage){
            this.rawMessage = rawMessage;
            return this;
        }
        
        public Builder sender(Sender sender){
            this.sender = sender;
            return this;
        }
        
        public Builder botId(long botId){
            this.botId = botId;
            return this;
        }
        
        public Builder groupId(long groupId){
            this.groupId = groupId;
            return this;
        }
        
        public Builder channelId(long channelId){
            this.channelId = channelId;
            return this;
        }
        
        public Builder userId(long userId){
            this.userId = userId;
            return this;
        }
    
        public Builder messageId(long messageId){
            this.messageId = messageId;
            return this;
        }
    
        public Builder operatorId(long operatorId){
            this.operatorId = operatorId;
            return this;
        }
    
        public Builder targetId(long targetId){
            this.targetId = targetId;
            return this;
        }
        
        public Builder realId(long realId){
            this.realId = realId;
            return this;
        }
    
        public Builder comment(String comment){
            this.comment = comment;
            return this;
        }
        
//        public Builder destruct(boolean destruct){
//            this.destruct = destruct;
//            return this;
//        }
        
        public Builder duration(long duration){
            this.duration = duration;
            return this;
        }
    
        public Builder extendData(Object extendData){
            this.extendData = extendData;
            return this;
        }
    
        public BotEvent build(){
            return new BotEvent(this);
        }
    }
    
    public EventType eventType(){
        return eventType;
    }
    
    public SourceType sourceType(){
        return sourceType;
    }
    
    public SenderType senderType(){
        return senderType;
    }
    
    public BotMessage[] messages(){
        return messages;
    }
    
    public String rawMessage(){
        return rawMessage;
    }
    
    public Sender sender(){
        return sender;
    }
    
    public long botId(){
        return botId;
    }
    
    public long groupId(){
        return groupId;
    }
    
    public long channelId(){
        return channelId;
    }
    
    public long userId(){
        return userId;
    }
    
    public long messageId(){
        return messageId;
    }
    
    public long operatorId(){
        return operatorId;
    }
    
    public long targetId(){
        return targetId;
    }
    
    public long realId(){
        return realId;
    }
    
    public String comment(){
        return comment;
    }
    
//    public boolean isDestruct(){
//        return destruct;
//    }
    
    public long duration(){
        return duration;
    }
    
    public Object extendData(){
        return extendData;
    }
    
}
