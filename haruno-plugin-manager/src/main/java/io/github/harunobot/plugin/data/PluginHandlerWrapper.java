/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.PluginHandler;
import io.github.harunobot.plugin.data.type.PluginRecivevType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginHandlerWrapper extends PluginWrapper {
    private final PluginHandler handler;
    
    public PluginHandlerWrapper(String id, PluginRecivevType recivevType, PluginAccessControlWrapper acl, PluginHandler handler){
        super(id, recivevType, acl);
        this.handler = handler;
    }
    
    public PluginHandler handler(){
        return handler;
    }
    
}
