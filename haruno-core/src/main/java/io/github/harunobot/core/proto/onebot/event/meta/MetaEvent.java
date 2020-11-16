/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.meta;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.proto.onebot.event.type.MetaEventType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;

/**
 *
 * @author iTeam_VEP
 */
public class MetaEvent {
    @JsonProperty(value="meta_event_type")
    private MetaEventType metaEventType;
    @JsonProperty(value="sub_type")
    private SubType subType;
    private Object status;
    private long interval;

    /**
     * @return the metaEventType
     */
    public MetaEventType getMetaEventType() {
        return metaEventType;
    }

    /**
     * @param metaEventType the metaEventType to set
     */
    public void setMetaEventType(MetaEventType metaEventType) {
        this.metaEventType = metaEventType;
    }

    /**
     * @return the subType
     */
    public SubType getSubType() {
        return subType;
    }

    /**
     * @param subType the subType to set
     */
    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    /**
     * @return the status
     */
    public Object getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }
}
