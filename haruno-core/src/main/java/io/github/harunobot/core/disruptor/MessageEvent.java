/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.disruptor;

/**
 *
 * @author iTeam_VEP
 */
public class MessageEvent {
    private long value;

    public void set(long value)
    {
        this.value = value;
    }
    
    void clear()
    {
        value = 0;
    }
}
