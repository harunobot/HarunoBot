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
public enum OutFormat {
    @JsonProperty(value="mp3")
    MP3,
    @JsonProperty(value="amr")
    AMR,
    @JsonProperty(value="wma")
    WMA,
    @JsonProperty(value="m4a")
    M4A,
    @JsonProperty(value="spx")
    SPX,
    @JsonProperty(value="ogg")
    OGG,
    @JsonProperty(value="wav")
    WAV,
    @JsonProperty(value="flac")
    FLAC
    ;
}
