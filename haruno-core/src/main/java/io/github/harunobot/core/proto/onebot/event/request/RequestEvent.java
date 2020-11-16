/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.proto.onebot.event.type.NoticeType;
import io.github.harunobot.core.proto.onebot.event.type.RequestType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;

/**
 *
 * @author iTeam_VEP
 */
public class RequestEvent {
    @JsonProperty(value="request_type")
    private RequestType requestType;
    @JsonProperty(value="user_id")
    private long userId;
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="sub_type")
    private SubType subType;
    private String comment;
    private String flag;

    /**
     * @return the requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the groupId
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

}
