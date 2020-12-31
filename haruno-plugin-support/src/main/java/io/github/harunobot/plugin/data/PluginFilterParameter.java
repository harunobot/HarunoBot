/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.PluginReceivedType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginFilterParameter {
    private final int priority;
    private final String name;
    private final PluginReceivedType receivedType;
    
    public PluginFilterParameter(PluginReceivedType receivedType, String name, int priority){
        this.receivedType = receivedType;
        this.name = name;
        this.priority = priority;
    }
    
    public PluginReceivedType receivedType(){
        return receivedType;
    }
    
    public String name(){
        return name;
    }
    
    public int priority(){
        return priority;
    }
    
}
