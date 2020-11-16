/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iTeam_VEP
 */
public enum ArrayMessageDataType {
    @JsonProperty(value="text")
    TEXT("text"),
    @JsonProperty(value="face")
    FACE("id"),
    @JsonProperty(value="image")
    IMAGE("file", "type", "url"),
    @JsonProperty(value="record")
    RECORD("file", "magic", "url"),
    @JsonProperty(value="video")
    VIDEO("file", "url"),
    @JsonProperty(value="at")
    AT("qq"),
    @JsonProperty(value="rps")
    RPS(),
    @JsonProperty(value="dice")
    DICE(),
    @JsonProperty(value="shake")
    SHAKE("qq"),
    @JsonProperty(value="poke")
    POKE("type", "id"),
    @JsonProperty(value="anonymous")
    ANONYMOUS(),
    @JsonProperty(value="share")
    SHARE("url", "title", "content", "image"),
    @JsonProperty(value="contact")
    CONTACT("type", "id"),
    @JsonProperty(value="location")
    LOCATION("lat", "lon", "title", "content"),
    @JsonProperty(value="reply")
    REPLY("id"),
    @JsonProperty(value="forward")
    FORWARD("id"),
    @JsonProperty(value="node")
    NODE("user_id", "nickname", "content"),
    @JsonProperty(value="xml")
    XML("data"),
    @JsonProperty(value="json")
    JSON("data")
    ;
    
    private final String[] key;
    
    ArrayMessageDataType(String ...key){
        this.key = key;
    }
    
    public String[] key(){
        return key;
    }
    
}
