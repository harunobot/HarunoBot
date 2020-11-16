/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.disruptor;

import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.nio.ByteBuffer;

/**
 *
 * @author iTeam_VEP
 */
public class DisruptorManger {
    
    public static void handleEvent(MessageEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println(event);
    }

    public static void translate(MessageEvent event, long sequence, ByteBuffer buffer)
    {
        event.set(buffer.getLong(0));
    }
    
    public void main() throws Exception {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<MessageEvent> disruptor = new Disruptor<>(MessageEvent::new, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(DisruptorManger::handleEvent).then(new ClearingMessageEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<MessageEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++)
        {
            bb.putLong(0, l);
            ringBuffer.publishEvent(DisruptorManger::translate, bb);
            Thread.sleep(1000);
        }
    }
}
