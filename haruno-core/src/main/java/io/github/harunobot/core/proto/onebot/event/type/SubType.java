/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * NOTICE BAN_RESCISED equals lift_ban
 * @author iTeam_VEP
 */
public enum SubType {
    @JsonProperty(value="add")
    ADD,
    @JsonProperty(value="friend")
    FRIEND,
    @JsonProperty(value="group")
    GROUP,
    @JsonProperty(value="other")
    OTHER,
    @JsonProperty(value="normal")
    NORMAL,
    @JsonProperty(value="anonymous")
    ANONYMOUS,
    @JsonProperty(value="notice")
    NOTICE,
    @JsonProperty(value="set")
    SET,
    @JsonProperty(value="unset")
    UNSET,
    @JsonProperty(value="leave")
    LEAVE,
    @JsonProperty(value="kick")
    KICK,
    @JsonProperty(value="kick_me")
    KICK_ME,
    @JsonProperty(value="approve")
    APPROVE,
    @JsonProperty(value="invite")
    INVITE,
    @JsonProperty(value="ban")
    BAN,
    @JsonProperty(value="lift_ban")
    BAN_RESCISED,
    @JsonProperty(value="poke")
    POKE,
    @JsonProperty(value="lucky_king")
    LUCKY_KING,
    @JsonProperty(value="honor")
    HONOR,
    @JsonProperty(value="enable")
    ENABLE,
    @JsonProperty(value="disable")
    DISABLE,
    @JsonProperty(value="connect")
    CONNECT,
    ;
    
}
