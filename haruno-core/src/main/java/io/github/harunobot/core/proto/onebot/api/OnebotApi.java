/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.api;

import java.util.Map;
import io.github.harunobot.core.proto.onebot.api.type.ApiAction;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotApi {
    private String action;
    private Map<String, Object> params;
    private long echo;
    
    public OnebotApi(){}
    
    public OnebotApi(ApiAction apiAction, Map<String, Object> params, long echo){
        this.action = apiAction.action();
        this.params = params;
        this.echo = echo;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params the params to set
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * @return the echo
     */
    public long getEcho() {
        return echo;
    }

    /**
     * @param echo the echo to set
     */
    public void setEcho(long echo) {
        this.echo = echo;
    }
    
}
