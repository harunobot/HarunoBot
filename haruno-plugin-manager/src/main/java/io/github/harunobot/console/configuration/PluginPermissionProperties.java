/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.console.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.plugin.data.type.Permission;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author iTeam_VEP
 */
public class PluginPermissionProperties {
    private String id;
    private Set<Permission> permissions;
    @JsonProperty(value="private")
    private Set<Long> privateUser;
    @JsonProperty(value="public")
    private Map<Long, Map<Long, Set<Long>>> publicUser;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the permissions
     */
    public Set<Permission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the privateUser
     */
    public Set<Long> getPrivateUser() {
        return privateUser;
    }

    /**
     * @param privateUser the privateUser to set
     */
    public void setPrivateUser(Set<Long> privateUser) {
        this.privateUser = privateUser;
    }

    /**
     * @return the publicUser
     */
    public Map<Long, Map<Long, Set<Long>>> getPublicUser() {
        return publicUser;
    }

    /**
     * @param publicUser the publicUser to set
     */
    public void setPublicUser(Map<Long, Map<Long, Set<Long>>> publicUser) {
        this.publicUser = publicUser;
    }
    
}
