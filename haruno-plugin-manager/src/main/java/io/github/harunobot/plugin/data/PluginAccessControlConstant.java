/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

/**
 *
 * @author iTeam_VEP
 */
public enum PluginAccessControlConstant {
    GLOBAL_ID(0L),
    ;
    
    private final long value;
    
    PluginAccessControlConstant(long value){
        this.value = value;
    }
    
    public long value(){
        return value;
    }
    
}
