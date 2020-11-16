/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.Permission;
import io.github.harunobot.plugin.data.type.PluginRecivevType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginWrapper {
    private final String id;
    private final PluginAccessControlWrapper acl;
    private final PluginRecivevType recivevType;
    
    public PluginWrapper(String id, PluginRecivevType recivevType, PluginAccessControlWrapper acl){
        this.id = id;
        this.acl = acl;
        this.recivevType = recivevType;
    }
    
    public boolean allow(PluginRecivevType recivevType, Permission permission, long server, long channel, long user){
        if(this.recivevType != recivevType && this.recivevType != PluginRecivevType.BOTH){
            return false;
        }
        if(PluginRecivevType.PRIVATE == recivevType){
            return acl.allowPrivate(permission, user);
        }
        if(PluginRecivevType.PUBLIC == recivevType){
            return acl.allowPublic(permission, server, channel, user);
        }
        return false;
    }
    
    public String id(){
        return id;
    }
    
    public PluginAccessControlWrapper acl(){
        return acl;
    }
    
}
