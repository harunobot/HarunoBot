/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.proto.onebot.event.type.HonorType;
import io.github.harunobot.core.proto.onebot.event.type.NoticeType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;

/**
 *
 * @author iTeam_VEP
 */
public class NoticeEvent {
    @JsonProperty(value="notice_type")
    private NoticeType noticeType;
    @JsonProperty(value="sub_type")
    private SubType subType;
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="user_id")
    private long userId;
    @JsonProperty(value="operator_id")
    private long operatorId;
    @JsonProperty(value="message_id")
    private long messageId;
    @JsonProperty(value="target_id")
    private long targetId;
    @JsonProperty(value="honor_type")
    private HonorType honorType;
    private OnebotFile file;
    private long duration;

    /**
     * @return the noticeType
     */
    public NoticeType getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType the noticeType to set
     */
    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
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
     * @return the operatorId
     */
    public long getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the messageId
     */
    public long getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the targetId
     */
    public long getTargetId() {
        return targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    /**
     * @return the honorType
     */
    public HonorType getHonorType() {
        return honorType;
    }

    /**
     * @param honorType the honorType to set
     */
    public void setHonorType(HonorType honorType) {
        this.honorType = honorType;
    }

    /**
     * @return the file
     */
    public OnebotFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(OnebotFile file) {
        this.file = file;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    
}
