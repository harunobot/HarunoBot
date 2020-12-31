/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.console.configuration.PluginPermissionProperties;
import io.github.harunobot.plugin.data.type.BlockType;
import io.github.harunobot.pojo.type.Permission;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author iTeam_VEP
 */
public class PluginPermissionsWrapper {
    
    private final String id;
    private final Set<Permission> permissions;
    private final Set<Long> privateUser;
    private final Map<Long, Map<Long, Set<Long>>> publicUser;
    
    public PluginPermissionsWrapper(String id, Set<Permission> permissions, PluginPermissionProperties pluginPermissionProperties){
        this.id = id;
        this.permissions = permissions;
        if(pluginPermissionProperties != null){
            this.privateUser = pluginPermissionProperties.getPrivateUser();
            this.publicUser = pluginPermissionProperties.getPublicUser();
        } else {
            this.privateUser = null;
            this.publicUser = null;
        }
    }
    
    public boolean allow(Permission permission){
        return permissions.contains(permission);
    }
    
    public String id(){
        return id;
    }
    
    public Set<Permission> permissions(){
        return permissions;
    }
    
    public Set<Long> privateUser(){
        return privateUser;
    }
    
    public Map<Long, Map<Long, Set<Long>>> publicUser(){
        return publicUser;
    }
}
