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
public class BotMessageRecord implements java.io.Serializable {
    private MessageContentType record;
    private String data;
    private String file;
    private String url;
    private String type;
    private boolean magic;
    private boolean destruct;
    
    public BotMessageRecord(){}
    
    public BotMessageRecord(BotMessage botMessage){
        this.record = botMessage.messageType();
        this.data = botMessage.data();
        this.file = botMessage.file();
        this.url = botMessage.url();
        this.type = botMessage.type();
        this.magic = botMessage.magic();
        this.destruct = botMessage.destruct();
    }
    
    public static BotMessageRecord[] convertBotMessages(BotMessage[] botMessages){
        if(botMessages == null){
            return null;
        }
        BotMessageRecord[] records = new BotMessageRecord[botMessages.length];
        for(int i=0; i<botMessages.length; i++){
            records[i] = new BotMessageRecord(botMessages[i]);
        }
        return records;
    }

    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * @return the file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the magic
     */
    public boolean isMagic() {
        return magic;
    }

    /**
     * @param magic the magic to set
     */
    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    /**
     * @return the destruct
     */
    public boolean isDestruct() {
        return destruct;
    }

    /**
     * @param destruct the destruct to set
     */
    public void setDestruct(boolean destruct) {
        this.destruct = destruct;
    }

    /**
     * @return the record
     */
    public MessageContentType getRecord() {
        return record;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(MessageContentType record) {
        this.record = record;
    }
}
