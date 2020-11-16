/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 *
 * @author iTeam_VEP
 */
public class MessageEventFactory implements EventFactory<MessageEvent>
{
    @Override
    public MessageEvent newInstance()
    {
        return new MessageEvent();
    }
}
