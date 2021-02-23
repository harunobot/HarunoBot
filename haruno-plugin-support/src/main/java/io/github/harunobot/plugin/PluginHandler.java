/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin;

import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.request.BotRequest;

/**
 *
 * @author iTeam_VEP
 */
public interface PluginHandler {
    BotRequest handle(String parameter, BotEvent event);
}
