/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.PluginRecivevType;
import io.github.harunobot.plugin.data.type.Permission;
import io.github.harunobot.plugin.data.type.PluginMatcherType;
import io.github.harunobot.plugin.data.type.PluginReceivedType;
import io.github.harunobot.plugin.data.type.PluginTextType;
import java.util.Set;

/**
 *
 * @author iTeam_VEP
 */
public class PluginFilterParameter {
    private final int priority;
    private final String name;
    private final PluginRecivevType recivevType;
    
    public PluginFilterParameter(PluginRecivevType recivevType, String name, int priority){
        this.recivevType = recivevType;
        this.name = name;
        this.priority = priority;
    }
    
    public PluginRecivevType recivevType(){
        return recivevType;
    }
    
    public String name(){
        return name;
    }
    
    public int priority(){
        return priority;
    }
    
}
