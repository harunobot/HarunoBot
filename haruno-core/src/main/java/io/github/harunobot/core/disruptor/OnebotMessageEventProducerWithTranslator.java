/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.EventTranslatorOneArg;
import java.nio.ByteBuffer;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotMessageEventProducerWithTranslator {
    private final RingBuffer<MessageEvent> ringBuffer;
    
    public OnebotMessageEventProducerWithTranslator(RingBuffer<MessageEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }
    
    private static final EventTranslatorOneArg<MessageEvent, ByteBuffer> TRANSLATOR =
        new EventTranslatorOneArg<MessageEvent, ByteBuffer>()
        {
            public void translateTo(MessageEvent event, long sequence, ByteBuffer bb)
            {
                event.set(bb.getLong(0));
            }
        };

    public void onData(ByteBuffer bb)
    {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
