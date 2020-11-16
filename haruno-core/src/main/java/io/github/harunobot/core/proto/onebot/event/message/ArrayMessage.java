/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.message;

import io.github.harunobot.core.proto.onebot.event.type.ArrayMessageDataType;
import java.util.Map;

/**
 *
 * @author iTeam_VEP
 */
public class ArrayMessage {
    private ArrayMessageDataType type;
    private Map<String, Object> data;

    /**
     * @return the type
     */
    public ArrayMessageDataType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ArrayMessageDataType type) {
        this.type = type;
    }

    /**
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
