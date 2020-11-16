/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.proto.event;

import io.github.harunobot.proto.event.type.MessageContentType;

/**
 *
 * @author iTeam_VEP
 */
public class BotMessage {
    private final MessageContentType messageType;
    private final String data;
    private final String file;
    private final String url;
    private final boolean magic;
    
    public BotMessage(Builder builder){
        this.messageType = builder.messageType;
        this.data = builder.data;
        this.file = builder.file;
        this.url = builder.url;
        this.magic = builder.magic;
    }
    
    public static class Builder {
        private MessageContentType messageType;
        private String data;
        private String file;
        private String url;
        private boolean magic;
        
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
        
        public Builder magic(boolean magic){
            this.magic = magic;
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
    
    public boolean magic(){
        return magic;
    }
    
}
