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
    private final PluginReceivedType recivevType;
    private final String trait;
    
    public PluginHandlerMatcher(PluginReceivedType recivevType, PluginMatcherType matcherType, PluginTextType textType, String trait){
        this.recivevType = recivevType;
        this.textType = textType;
        this.matcherType = matcherType;
        this.trait = trait;
    }
    
    public PluginReceivedType recivevType(){
        return recivevType;
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
    
}
