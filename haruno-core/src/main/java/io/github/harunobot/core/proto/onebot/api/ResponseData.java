/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.harunobot.core.proto.onebot.event.message.Sender;
import io.github.harunobot.core.proto.onebot.event.type.MessageType;

/**
 *
 * @author iTeam_VEP
 */
public class ResponseData {
    @JsonProperty(value="message_id")
    private int messageId;
    @JsonProperty(value="time")
    private int time;
    @JsonProperty(value="message_type")
    private MessageType messageType;
    @JsonProperty(value="real_id")
    private long realId;
    @JsonProperty(value="sender")
    private Sender sender;
    @JsonProperty(value="message")
    private Object message;
    @JsonProperty(value="user_id")
    private long userId;
    @JsonProperty(value="nickname")
    private String nickname;
    @JsonProperty(value="sex")
    private String sex;
    @JsonProperty(value="age")
    private int age;
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="group_name")
    private long groupName;
    @JsonProperty(value="member_count")
    private long memberCount;
    @JsonProperty(value="max_member_count")
    private long maxMemberCount;
    @JsonProperty(value="card")
    private long card;
    @JsonProperty(value="area")
    private long area;
    @JsonProperty(value="join_time")
    private int joinTime;
    @JsonProperty(value="last_sent_time")
    private int lastSentTime;
    @JsonProperty(value="level")
    private String level;
    @JsonProperty(value="role")
    private String role;
    @JsonProperty(value="unfriendly")
    private boolean unfriendly;
    @JsonProperty(value="title")
    private String title;
    @JsonProperty(value="title_expire_time")
    private int titleExpireTime;
    @JsonProperty(value="card_changeable")
    private boolean cardChangeable;

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
     * @return the time
     */
    public int getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(int time) {
        this.time = time;
    }

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
     * @return the realId
     */
    public long getRealId() {
        return realId;
    }

    /**
     * @param realId the realId to set
     */
    public void setRealId(long realId) {
        this.realId = realId;
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

    /**
     * @return the message
     */
    public Object getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Object message) {
        this.message = message;
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
     * @return the nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname the nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(int age) {
        this.age = age;
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
     * @return the groupName
     */
    public long getGroupName() {
        return groupName;
    }

    /**
     * @param groupName the groupName to set
     */
    public void setGroupName(long groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the memberCount
     */
    public long getMemberCount() {
        return memberCount;
    }

    /**
     * @param memberCount the memberCount to set
     */
    public void setMemberCount(long memberCount) {
        this.memberCount = memberCount;
    }

    /**
     * @return the maxMemberCount
     */
    public long getMaxMemberCount() {
        return maxMemberCount;
    }

    /**
     * @param maxMemberCount the maxMemberCount to set
     */
    public void setMaxMemberCount(long maxMemberCount) {
        this.maxMemberCount = maxMemberCount;
    }

    /**
     * @return the card
     */
    public long getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(long card) {
        this.card = card;
    }

    /**
     * @return the area
     */
    public long getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(long area) {
        this.area = area;
    }

    /**
     * @return the joinTime
     */
    public int getJoinTime() {
        return joinTime;
    }

    /**
     * @param joinTime the joinTime to set
     */
    public void setJoinTime(int joinTime) {
        this.joinTime = joinTime;
    }

    /**
     * @return the lastSentTime
     */
    public int getLastSentTime() {
        return lastSentTime;
    }

    /**
     * @param lastSentTime the lastSentTime to set
     */
    public void setLastSentTime(int lastSentTime) {
        this.lastSentTime = lastSentTime;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level the level to set
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @return the unfriendly
     */
    public boolean isUnfriendly() {
        return unfriendly;
    }

    /**
     * @param unfriendly the unfriendly to set
     */
    public void setUnfriendly(boolean unfriendly) {
        this.unfriendly = unfriendly;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the titleExpireTime
     */
    public int getTitleExpireTime() {
        return titleExpireTime;
    }

    /**
     * @param titleExpireTime the titleExpireTime to set
     */
    public void setTitleExpireTime(int titleExpireTime) {
        this.titleExpireTime = titleExpireTime;
    }

    /**
     * @return the cardChangeable
     */
    public boolean isCardChangeable() {
        return cardChangeable;
    }

    /**
     * @param cardChangeable the cardChangeable to set
     */
    public void setCardChangeable(boolean cardChangeable) {
        this.cardChangeable = cardChangeable;
    }
    
}
