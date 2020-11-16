/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.BlockType;
import io.github.harunobot.plugin.data.type.Permission;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class PluginAccessControlWrapper {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(PluginAccessControlWrapper.class);
    
    private final String id;
    private final Map<Long, Map<Long, Set<Long>>> publicUser;
    private final Set<Long> privateUser;
    private final BlockType blockType;
    private final Set<Permission> permissions;
    
    public PluginAccessControlWrapper(String id, Set<Permission> permissions, Map<Long, Map<Long, Set<Long>>> publicUser, Set<Long> privateUser, BlockType blockType){
        this.id = id;
        this.permissions = permissions;
        this.publicUser = publicUser;
        this.privateUser = privateUser;
        this.blockType = blockType;
    }
    
    public boolean allowPrivate(Permission permission, long user){
        if(!permissions.contains(permission)){
            LOG.info("private {} lack of {} permission", id, permission);
            return false;
        }
        if(privateUser == null) {
            return true;
        }
        if(user == PluginAccessControlConstant.GLOBAL_ID.value()){
            return true;
        }
        if(blockType == BlockType.BLACK_LIST){
            return !privateUser.contains(user);
        }
        if(blockType == BlockType.WHITE_LIST){
            return privateUser.contains(user);
        }
        return false;
    }
    
    public boolean allowPublic(Permission permission, long server, long channel, long user){
        if(!permissions.contains(permission)){
            LOG.info("public {} lack of {} permission", id, permission);
            return false;
        }
        if(publicUser == null){
            return true;
        }
        if(user == PluginAccessControlConstant.GLOBAL_ID.value()){
            return true;
        }
        if(blockType == BlockType.BLACK_LIST){
            if(!publicUser.containsKey(server)){
                return true;
            }
            Set<Long> users = publicUser.get(server).get(channel);
            if(users == null){
                return true;
            }
            if(users.contains(PluginAccessControlConstant.GLOBAL_ID.value())){
                return false;
            }
            return !users.contains(user);
        }
        if(blockType == BlockType.WHITE_LIST){
            if(!publicUser.containsKey(server)){
                return false;
            }
            Set<Long> users = publicUser.get(server).get(channel);
            if(users == null){
                return false;
            }
            if(users.contains(PluginAccessControlConstant.GLOBAL_ID.value())){
                return true;
            }
            return users.contains(user);
        }
        return false;
    }
    
    public String id(){
        return id;
    }
    
}
