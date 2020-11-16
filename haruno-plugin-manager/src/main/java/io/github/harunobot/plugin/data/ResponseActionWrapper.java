/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.data;

import io.github.harunobot.plugin.data.type.ResponseActionType;

/**
 *
 * @author iTeam_VEP
 */
public class ResponseActionWrapper {
    private long serialId;
    private String pluginId;
    private ResponseActionType action;
    private int duration;
    
    public ResponseActionWrapper(long serialId, int duration){
        this.serialId = serialId;
        this.duration = duration;
        this.action = ResponseActionType.DELETE;
    }
    
    public ResponseActionWrapper(long serialId, String pluginId, ResponseActionType action){
        this.serialId = serialId;
        this.pluginId = pluginId;
        this.action = action;
    }

    /**
     * @return the serialId
     */
    public long getSerialId() {
        return serialId;
    }

    /**
     * @param serialId the serialId to set
     */
    public void setSerialId(long serialId) {
        this.serialId = serialId;
    }

    /**
     * @return the pluginId
     */
    public String getPluginId() {
        return pluginId;
    }

    /**
     * @param pluginId the pluginId to set
     */
    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * @return the action
     */
    public ResponseActionType getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(ResponseActionType action) {
        this.action = action;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
