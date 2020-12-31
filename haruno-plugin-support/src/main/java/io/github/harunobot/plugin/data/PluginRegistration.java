/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.PluginFilter;
import io.github.harunobot.plugin.PluginHandler;
import io.github.harunobot.pojo.type.Permission;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author iTeam_VEP
 */
public final class PluginRegistration {
    private Map<PluginHandlerMatcher, PluginHandler> handlers;
    private Map<PluginFilterParameter, PluginFilter> filters;
    private PluginHandler interactiveHandler;
    private Set<Permission> permissions; //java.util.Collections.unmodifiableSet()

    public PluginRegistration(Set<Permission> permissions
            , Map<PluginHandlerMatcher, PluginHandler> handlers
            , Map<PluginFilterParameter, PluginFilter> filters){
        this.permissions = permissions==null?null:Collections.unmodifiableSet(permissions);
        this.handlers = handlers;
        this.filters = filters;
    }
    
    public PluginRegistration(Set<Permission> permissions
            , Map<PluginHandlerMatcher, PluginHandler> handlers
            , Map<PluginFilterParameter, PluginFilter> filters
            , PluginHandler interactiveHandler){
        this.permissions = permissions==null?null:Collections.unmodifiableSet(permissions);
        this.handlers = handlers;
        this.filters = filters;
        this.interactiveHandler = interactiveHandler;
    }
    
    public Map<PluginHandlerMatcher, PluginHandler> handlers(){
        return handlers;
    }
    
    public Map<PluginFilterParameter, PluginFilter> filters(){
        return filters;
    }
    
    public Set<Permission> permissions(){
        return permissions;
    }
    
    public PluginHandler interactiveHandler(){
        return interactiveHandler;
    }
    
}
