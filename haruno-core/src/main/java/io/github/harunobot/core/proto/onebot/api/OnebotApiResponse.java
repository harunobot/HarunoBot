/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.api;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import io.github.harunobot.core.proto.onebot.api.type.Status;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotApiResponse {
    
    private Status status;
    private int retcode;
//    private Map<String, Object> data;
    private JsonNode data;
    private long echo;
    private String msg;
    private String wording;

    /**
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * @return the retcode
     */
    public int getRetcode() {
        return retcode;
    }

    /**
     * @param retcode the retcode to set
     */
    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    /**
     * @return the data
     */
    public JsonNode getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(JsonNode data) {
        this.data = data;
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

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the wording
     */
    public String getWording() {
        return wording;
    }

    /**
     * @param wording the wording to set
     */
    public void setWording(String wording) {
        this.wording = wording;
    }
}
