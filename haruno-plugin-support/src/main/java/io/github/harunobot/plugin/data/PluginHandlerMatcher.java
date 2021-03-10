/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.PluginReceivedType;
import io.github.harunobot.plugin.data.type.PluginMatcherType;
import io.github.harunobot.plugin.data.type.PluginTextType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginHandlerMatcher {
    private final PluginTextType textType;
    private final PluginMatcherType matcherType;
    private final PluginReceivedType receivedType;
    private final String trait;
    private final String splitRegex;
    
    public PluginHandlerMatcher(PluginReceivedType receivedType, PluginMatcherType matcherType, PluginTextType textType, String trait){
        this.receivedType = receivedType;
        this.textType = textType;
        this.matcherType = matcherType;
        this.trait = trait;
        this.splitRegex = null;
    }
    
    public PluginHandlerMatcher(PluginReceivedType receivedType, String trait, String splitRegex){
        this.receivedType = receivedType;
        this.textType = null;
        this.matcherType = PluginMatcherType.COMMAND;
        this.trait = trait;
        this.splitRegex = splitRegex;
    }
    
    public PluginReceivedType receivedType(){
        return receivedType;
    }
    
    public PluginTextType textType(){
        return textType;
    }
    
    public PluginMatcherType matcherType(){
        return matcherType;
    }
    
    public String trait(){
        return trait;
    }
    
    public String splitRegex(){
        return splitRegex;
    }
    
}
