/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.proto.onebot.event.type.MessageType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;

/**
 *
 * @author iTeam_VEP
 */
public class MessageEvent {
    @JsonProperty(value="message_type")
    private MessageType messageType;
    @JsonProperty(value="sub_type")
    private SubType subType;
    @JsonProperty(value="message_id")
    private int messageId;
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="user_id")
    private long userId;
    private Anonymous anonymous;
    private String message;
    @JsonProperty(value="raw_message")
    private String rawMessage;
    private int font;
    private Sender sender;

    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
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
     * @return the messageId
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
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
     * @return the anonymous
     */
    public Anonymous getAnonymous() {
        return anonymous;
    }

    /**
     * @param anonymous the anonymous to set
     */
    public void setAnonymous(Anonymous anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the rawMessage
     */
    public String getRawMessage() {
        return rawMessage;
    }

    /**
     * @param rawMessage the rawMessage to set
     */
    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    /**
     * @return the font
     */
    public int getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(int font) {
        this.font = font;
    }

    /**
     * @return the sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
