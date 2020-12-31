/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.pojo.type.Permission;
import io.github.harunobot.plugin.data.type.PluginReceivedType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginWrapper {
    private final String id;
    private final PluginAccessControlWrapper acl;
    private final PluginReceivedType recivevType;
    
    public PluginWrapper(String id, PluginReceivedType recivevType, PluginAccessControlWrapper acl){
        this.id = id;
        this.acl = acl;
        this.recivevType = recivevType;
    }
    
    public boolean allow(PluginReceivedType recivevType, Permission permission, long server, long channel, long user){
        if(this.recivevType != recivevType && this.recivevType != PluginReceivedType.BOTH){
            return false;
        }
        if(PluginReceivedType.PRIVATE == recivevType){
            return acl.allowPrivate(permission, user);
        }
        if(PluginReceivedType.PUBLIC == recivevType){
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
