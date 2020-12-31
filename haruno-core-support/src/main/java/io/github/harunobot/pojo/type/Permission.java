/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.pojo.type;

/**
 *
 * @author iTeam_VEP
 */
public enum Permission {
    UNDEFINED,
    
    RECEIVE_PRIVATE_MESSAGE,
    RECEIVE_PUBLIC_MESSAGE,
    TRANSMIT_PRIVATE_MESSAGE,
    TRANSMIT_PUBLIC_MESSAGE,
    
    RECEIVE_PRIVATE_REQUEST,
    RECEIVE_PUBLIC_REQUEST,
    TRANSMIT_PRIVATE_REQUEST,
    TRANSMIT_PUBLIC_REQUEST,
    
//    RECEIVE_PUBLIC_NOTICE,
    PUBLIC_INCREASE_NOTICE,
    PUBLIC_DECREASE_NOTICE,
    
    MUTE,
    ;
}
