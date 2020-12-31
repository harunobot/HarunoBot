/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.PluginFilter;
import io.github.harunobot.plugin.data.type.PluginReceivedType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginFilterWrapper extends PluginWrapper {
    private final PluginFilter filter;
    private final int priority;
    
    public PluginFilterWrapper(String id, PluginReceivedType recivevType, PluginAccessControlWrapper acl, int priority, PluginFilter filter){
        super(id, recivevType, acl);
        this.priority = priority;
        this.filter = filter;
    }
    
    public PluginFilter filter(){
        return filter;
    }
    
    public int priority(){
        return priority;
    }
    
}
