/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.proto.request;

import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.event.BotMessage;
import io.github.harunobot.proto.request.type.RequestType;

/**
 *
 * @author iTeam_VEP
 */
public class BotRequest {

    private RequestType requestType;
    private BotMessage[] messages;
    private long botId;
    private long groupId;
    private long channelId;
    private long userId;
    private long messageId;
    private int alivetime;
    private int muteDuration;
    private boolean approve;
    private String info;
    private BotEvent botEvent;
    private Object extendData;
    
    public BotRequest(Builder builder){
        this.requestType = builder.requestType;
        this.messages = builder.messages;
        this.botId = builder.botId;
        this.groupId = builder.groupId;
        this.channelId = builder.channelId;
        this.userId = builder.userId;
        this.messageId = builder.messageId;
        this.extendData = builder.extendData;
        this.alivetime = builder.alivetime;
        this.approve = builder.approve;
        this.info = builder.info;
        this.botEvent = builder.botEvent;
        this.muteDuration = builder.muteDuration;
    }
    
    public static class Builder {
        private RequestType requestType;
        private BotMessage[] messages;
        private long botId;
        private long groupId;
        private long channelId;
        private long userId;
        private long messageId;
        private int alivetime;
        private int muteDuration;
        private boolean approve;
        private String info;
        private BotEvent botEvent;
        private Object extendData;
        
        public Builder requestType(RequestType requestType){
            this.requestType = requestType;
            return this;
        }
        
        public Builder messages(BotMessage[] messages){
            this.messages = messages;
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
        
        public Builder alivetime(int alivetime){
            this.alivetime = alivetime;
            return this;
        }
        
        public Builder approve(boolean approve){
            this.approve = approve;
            return this;
        }
        
        public Builder muteDuration(int muteDuration){
            this.muteDuration = muteDuration;
            return this;
        }
        
        public Builder info(String info){
            this.info = info;
            return this;
        }
        
        public Builder botEvent(BotEvent botEvent){
            this.botEvent = botEvent;
            return this;
        }
    
        public Builder extendData(Object extendData){
            this.extendData = extendData;
            return this;
        }
    
        public BotRequest build(){
            return new BotRequest(this);
        }
    }
    
//    public RequestType requestType(){
//        return requestType;
//    }
//    
//    public BotMessage[] messages(){
//        return messages;
//    }
//    
//    public long botId(){
//        return botId;
//    }
//    
//    public long groupId(){
//        return groupId;
//    }
//    
//    public long channelId(){
//        return channelId;
//    }
//    
//    public long userId(){
//        return userId;
//    }
//    
//    public long duration(){
//        return duration;
//    }
//    
//    public Object extendData(){
//        return extendData;
//    }

    /**
     * @return the requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the messages
     */
    public BotMessage[] getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(BotMessage[] messages) {
        this.messages = messages;
    }

    /**
     * @return the botId
     */
    public long getBotId() {
        return botId;
    }

    /**
     * @param botId the botId to set
     */
    public void setBotId(long botId) {
        this.botId = botId;
    }

    /**
     * @return the groupId
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the channelId
     */
    public long getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the extendData
     */
    public Object getExtendData() {
        return extendData;
    }

    /**
     * @param extendData the extendData to set
     */
    public void setExtendData(Object extendData) {
        this.extendData = extendData;
    }

    /**
     * @return the muteDuration
     */
    public int getMuteDuration() {
        return muteDuration;
    }

    /**
     * @param muteDuration the muteDuration to set
     */
    public void setMuteDuration(int muteDuration) {
        this.muteDuration = muteDuration;
    }
    
    /**
     * @return the alivetime
     */
    public int getAlivetime() {
        return alivetime;
    }

    /**
     * @param alivetime the alivetime to set
     */
    public void setAlivetime(int alivetime) {
        this.alivetime = alivetime;
    }

    /**
     * @return the messageId
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the approve
     */
    public boolean isApprove() {
        return approve;
    }

    /**
     * @param approve the approve to set
     */
    public void setApprove(boolean approve) {
        this.approve = approve;
    }

    /**
     * @return the botEvent
     */
    public BotEvent getBotEvent() {
        return botEvent;
    }

    /**
     * @param botEvent the botEvent to set
     */
    public void setBotEvent(BotEvent botEvent) {
        this.botEvent = botEvent;
    }

    /**
     * @return the info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info the info to set
     */
    public void setInfo(String info) {
        this.info = info;
    }
    
}
