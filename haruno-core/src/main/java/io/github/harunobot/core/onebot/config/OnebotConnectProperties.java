/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.config;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotConnectProperties {
    private String authorization;
    private String host;
    private int port;
    private String uri;
    
    public OnebotConnectProperties(){}
    
    public OnebotConnectProperties(Builder builder){
        this.authorization = builder.authorization;
        this.host = builder.host;
        this.port = builder.port;
        this.uri = builder.uri;
    }
    
    public static class Builder {
        private String authorization;
        private String host;
        private int port;
        private String uri;
        
        public Builder authorization(String authorization){
            this.authorization = authorization;
            return this;
        }
        
        public Builder host(String host){
            this.host = host;
            return this;
        }
        
        public Builder port(int port){
            this.port = port;
            return this;
        }
        
        public Builder uri(String uri){
            this.uri = uri;
            return this;
        }
        
        public OnebotConnectProperties build(){
            return new OnebotConnectProperties(this);
        }
    }

    /**
     * @return the authorization
     */
    public String getAuthorization() {
        return authorization;
    }

    /**
     * @param authorization the authorization to set
     */
    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }
    
}
