/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 *
 * @author iTeam_VEP
 */
public class ClearingMessageEventHandler implements EventHandler<MessageEvent>
{
    @Override
    public void onEvent(MessageEvent event, long sequence, boolean endOfBatch)
    {
        // Failing to call clear here will result in the 
        // object associated with the event to live until
        // it is overwritten once the ring buffer has wrapped
        // around to the beginning.
        event.clear(); 
    }
}
