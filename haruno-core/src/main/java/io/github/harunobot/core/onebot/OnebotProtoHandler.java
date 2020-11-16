/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import io.github.harunobot.core.onebot.base.OnebotTransmitter;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;

/**
 *
 * @author iTeam_VEP
 */
public interface OnebotProtoHandler {

//    private OnebotTransmitter transmitter;
    
    default void handlePrivateMessage(OnebotEvent event){
        
    }
    
    default void handleGroupMessage(OnebotEvent event){
        
    }
    
    default void handleMetaEventMessage(OnebotEvent event){
        
    }
    
    default void handleNoticeMessage(OnebotEvent event){
        
    }
    
    default void handleRequestMessage(OnebotEvent event){
        
    }
    
    default void handleDefaultMessage(OnebotEvent event){
        
    }
    
    default void handleApiResponse(OnebotApiResponse apiResponse){
//        this.transmitter.transmit("");
    }
    
//    default void handleApiRequest(OnebotApi api){
//        
//    }
    
    void setTransmitter(OnebotTransmitter transmitter);
    
}
