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
public final class PluginDescription {
    private final String id;
    private final String version;
    private final String name;
    
    public PluginDescription(Builder builder){
        this.id = builder.id;
        this.version = builder.version;
        this.name = builder.name;
    }
    
    public static class Builder {
        private String id;
        private String version;
        private String name;
        
        public Builder id(String id){
            this.id = id;
            return this;
        }
        
        public Builder version(String version){
            this.version = version;
            return this;
        }
        
        public Builder name(String name){
            this.name = name;
            return this;
        }
        
        public PluginDescription build(){
            return new PluginDescription(this);
        }
    }
    
    public String id() {
        return id;
    }

    public String version() {
        return version;
    }
    
    public String name() {
        return name;
    }
    
}
