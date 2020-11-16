/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.console.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.onebot.config.OnebotConnectProperties;

/**
 *
 * @author iTeam_VEP
 */
public class BotProperties {
    @JsonProperty(value="onebot")
    private OnebotConnectProperties onebotConnection;
    @JsonProperty(value="httpserver")
    private HttpServerProperties httpServer;
    @JsonProperty(value="plugin")
    private PluginProperties Plugin;

    /**
     * @return the onebotConnection
     */
    public OnebotConnectProperties getOnebotConnection() {
        return onebotConnection;
    }

    /**
     * @param onebotConnection the onebotConnection to set
     */
    public void setOnebotConnection(OnebotConnectProperties onebotConnection) {
        this.onebotConnection = onebotConnection;
    }

    /**
     * @return the httpServer
     */
    public HttpServerProperties getHttpServer() {
        return httpServer;
    }

    /**
     * @param httpServer the httpServer to set
     */
    public void setHttpServer(HttpServerProperties httpServer) {
        this.httpServer = httpServer;
    }

    /**
     * @return the Plugin
     */
    public PluginProperties getPlugin() {
        return Plugin;
    }

    /**
     * @param Plugin the Plugin to set
     */
    public void setPlugin(PluginProperties Plugin) {
        this.Plugin = Plugin;
    }
}
