/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.proto.event;

import io.github.harunobot.proto.event.type.MessageContentType;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author iTeam_VEP
 */
public final class BotMessage {
    private final MessageContentType messageType;
    private final String data;
    private final String file;
    private final String url;
    private final String type;
    private final boolean magic;
    private final boolean destruct;
    
    public BotMessage(Builder builder){
        this.messageType = builder.messageType;
        this.data = builder.data;
        this.file = builder.file;
        this.url = builder.url;
        this.type = builder.type;
        this.magic = builder.magic;
        this.destruct = builder.destruct;
    }
    
    public BotMessage(BotMessageRecord record){
        this.messageType = record.getRecord();
        this.data = record.getData();
        this.file = record.getFile();
        this.url = record.getUrl();
        this.type = record.getType();
        this.magic = record.isMagic();
        this.destruct = record.isDestruct();
    }
    
    public static List<BotMessage> convertRecords(List<BotMessageRecord> records){
        if(records == null){
            return null;
        }
        return records.stream().map(record -> new BotMessage(record)).collect(Collectors.toList());
    }
    
    public static BotMessageRecord convertBotMessage(BotMessage botMessage){
        if(botMessage == null){
            return null;
        }
        BotMessageRecord record = new BotMessageRecord();
        record.setData(botMessage.data);
        record.setDestruct(botMessage.destruct);
        record.setFile(botMessage.file);
        record.setMagic(botMessage.magic);
        record.setRecord(botMessage.messageType);
        record.setType(botMessage.type);
        record.setUrl(botMessage.url);
        return record;
    }
    
    public static class Builder {
        private MessageContentType messageType;
        private String data;
        private String file;
        private String url;
        private String type;
        private boolean magic;
        private boolean destruct;
        
        public Builder messageType(MessageContentType messageType){
            this.messageType = messageType;
            return this;
        }
        
        public Builder data(String data){
            this.data = data;
            return this;
        }
        
        public Builder file(String file){
            this.file = file;
            return this;
        }
        
        public Builder url(String url){
            this.url = url;
            return this;
        }
        
        public Builder type(String type){
            this.type = type;
            return this;
        }
        
        public Builder magic(boolean magic){
            this.magic = magic;
            return this;
        }
        
        public Builder destruct(boolean destruct){
            this.destruct = destruct;
            return this;
        }
        
        public BotMessage build(){
            return new BotMessage(this);
        }
    }
    
    public MessageContentType messageType(){
        return messageType;
    }
    
    public String data(){
        return data;
    }
    
    public String file(){
        return file;
    }
    
    public String url(){
        return url;
    }
    
    public String type(){
        return type;
    }
    
    public boolean magic(){
        return magic;
    }
    
    public boolean destruct(){
        return destruct;
    }
    
}
