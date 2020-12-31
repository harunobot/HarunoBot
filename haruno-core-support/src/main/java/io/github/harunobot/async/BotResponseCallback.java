/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.async;

import io.github.harunobot.proto.response.BotResponse;

/**
 *
 * @author iTeam_VEP
 * @param <T> BotResponse data type
 */
public interface BotResponseCallback<T>  {
    void handle(BotResponse<T> botResponse);
}
