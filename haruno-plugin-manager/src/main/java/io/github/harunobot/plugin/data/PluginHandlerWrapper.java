/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import dk.brics.automaton.RunAutomaton;
import io.github.harunobot.plugin.PluginHandler;
import io.github.harunobot.plugin.data.type.PluginReceivedType;

/**
 *
 * @author iTeam_VEP
 */
public class PluginHandlerWrapper extends PluginWrapper {
    private final PluginHandler handler;
    private RunAutomaton regex;
    private String trait;
    private String splitRegex;
    
    public PluginHandlerWrapper(String id, PluginReceivedType recivevType, PluginAccessControlWrapper acl, PluginHandler handler){
        super(id, recivevType, acl);
        this.handler = handler;
    }
    
    public PluginHandler handler(){
        return handler;
    }

    /**
     * @return the regex
     */
    public RunAutomaton getRegex() {
        return regex;
    }

    /**
     * @param regex the regex to set
     */
    public void setRegex(RunAutomaton regex) {
        this.regex = regex;
    }

    /**
     * @return the trait
     */
    public String getTrait() {
        return trait;
    }

    /**
     * @param trait the trait to set
     */
    public void setTrait(String trait) {
        this.trait = trait;
    }

    /**
     * @return the splitRegex
     */
    public String getSplitRegex() {
        return splitRegex;
    }

    /**
     * @param splitRegex the splitRegex to set
     */
    public void setSplitRegex(String splitRegex) {
        this.splitRegex = splitRegex;
    }
    
}
