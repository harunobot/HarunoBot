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
public class PluginDetail {
    private String identifier;
    private String path;
    private PluginRegistration registration;
    
    public PluginDetail(){}
    
    public PluginDetail(String identifier, String path, PluginRegistration registration){
        this.identifier = identifier;
        this.path = path;
        this.registration = registration;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the registration
     */
    public PluginRegistration getRegistration() {
        return registration;
    }

    /**
     * @param registration the registration to set
     */
    public void setRegistration(PluginRegistration registration) {
        this.registration = registration;
    }
    
    
}
