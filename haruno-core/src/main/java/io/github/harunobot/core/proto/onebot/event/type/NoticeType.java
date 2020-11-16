/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event.type;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author iTeam_VEP
 */
public enum NoticeType {
    @JsonProperty(value="group_upload")
    GROUP_UPLOAD,
    @JsonProperty(value="group_admin")
    GROUP_ADMIN,
    @JsonProperty(value="group_decrease")
    GROUP_DECREASE,
    @JsonProperty(value="group_increase")
    GROUP_INCREASE,
    @JsonProperty(value="group_ban")
    GROUP_BAN,
    @JsonProperty(value="group_recall")
    GROUP_RECALL,
    @JsonProperty(value="friend_add")
    FRIEND_ADD,
    @JsonProperty(value="friend_recall")
    FRIEND_RECALL,
    @JsonProperty(value="notify")
    NOTIFY
    ;
    
    
}
