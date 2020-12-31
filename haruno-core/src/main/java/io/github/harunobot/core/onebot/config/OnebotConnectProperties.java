/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotConnectProperties {
    private String authorization;
    @JsonProperty(value="ws-host")
    private String wsHost;
    @JsonProperty(value="ws-port")
    private int wsPort;
    @JsonProperty(value="ws-uri")
    private String wsUri;
    @JsonProperty(value="http-host")
    private String httpHost;
    @JsonProperty(value="http-port")
    private int httpPort;
    @JsonProperty(value="http-uri")
    private String httpUri;
    
    public OnebotConnectProperties(){}
    
    public OnebotConnectProperties(Builder builder){
        this.authorization = builder.authorization;
        this.wsHost = builder.wsHost;
        this.wsPort = builder.wsPort;
        this.wsUri = builder.wsUri;
        this.httpHost = builder.httpHost;
        this.httpPort = builder.httpPort;
        this.httpUri = builder.httpUri;
    }
    
    public static class Builder {
        private String authorization;
        private String wsHost;
        private int wsPort;
        private String wsUri;
        private String httpHost;
        private int httpPort;
        private String httpUri;
        
        public Builder authorization(String authorization){
            this.authorization = authorization;
            return this;
        }
        
        public Builder wsHost(String wsHost){
            this.wsHost = wsHost;
            return this;
        }
        
        public Builder wsPort(int wsPort){
            this.wsPort = wsPort;
            return this;
        }
        
        public Builder wsUri(String wsUri){
            this.wsUri = wsUri;
            return this;
        }
        
        public Builder httpHost(String httpHost){
            this.httpHost = httpHost;
            return this;
        }
        
        public Builder httpPort(int httpPort){
            this.httpPort = httpPort;
            return this;
        }
        
        public Builder httpUri(String httpUri){
            this.httpUri = httpUri;
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
     * @return the wsHost
     */
    public String getWsHost() {
        return wsHost;
    }

    /**
     * @param wsHost the wsHost to set
     */
    public void setWsHost(String wsHost) {
        this.wsHost = wsHost;
    }

    /**
     * @return the wsPort
     */
    public int getWsPort() {
        return wsPort;
    }

    /**
     * @param wsPort the wsPort to set
     */
    public void setWsPort(int wsPort) {
        this.wsPort = wsPort;
    }

    /**
     * @return the wsUri
     */
    public String getWsUri() {
        return wsUri;
    }

    /**
     * @param wsUri the wsUri to set
     */
    public void setWsUri(String wsUri) {
        this.wsUri = wsUri;
    }

    /**
     * @return the httpHost
     */
    public String getHttpHost() {
        return httpHost;
    }

    /**
     * @param httpHost the httpHost to set
     */
    public void setHttpHost(String httpHost) {
        this.httpHost = httpHost;
    }

    /**
     * @return the httpPort
     */
    public int getHttpPort() {
        return httpPort;
    }

    /**
     * @param httpPort the httpPort to set
     */
    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    /**
     * @return the httpUri
     */
    public String getHttpUri() {
        return httpUri;
    }

    /**
     * @param httpUri the httpUri to set
     */
    public void setHttpUri(String httpUri) {
        this.httpUri = httpUri;
    }
    
}
