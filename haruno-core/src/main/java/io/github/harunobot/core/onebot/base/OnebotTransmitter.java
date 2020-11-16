/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.base;

import io.github.harunobot.core.proto.onebot.api.OnebotApi;

/**
 *
 * @author iTeam_VEP
 */
public interface OnebotTransmitter {
    
    public void transmit(OnebotApi api);
}
