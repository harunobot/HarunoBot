/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iTeam_VEP
 */
public class GroupMemberInfo {
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="user_id")
    private long userId;
    @JsonProperty(value="nickname")
    private String nickname;
    @JsonProperty(value="card")
    private String card;
    @JsonProperty(value="sex")
    private String sex;
    @JsonProperty(value="age")
    private int age;
    @JsonProperty(value="area")
    private String area;
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
     * @return the card
     */
    public String getCard() {
        return card;
    }

    /**
     * @param card the card to set
     */
    public void setCard(String card) {
        this.card = card;
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
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
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
