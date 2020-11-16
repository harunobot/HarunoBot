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
public enum HonorType {
    @JsonProperty(value="talkative")
    TALKATIVE,
    @JsonProperty(value="performer")
    PERFORMER,
    @JsonProperty(value="emotion")
    EMOTION
    ;
}
