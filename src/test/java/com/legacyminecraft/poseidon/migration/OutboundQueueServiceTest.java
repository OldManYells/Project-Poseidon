package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutboundQueueSystem;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OutboundQueueServiceTest {
    @Test
    public void enqueueRoutesByPriorityAndTracksQueuedBytes() {
        OutboundQueueSystem service = OutboundQueueSystem.getInstance();
        List highPriority = new ArrayList();
        List lowPriority = new ArrayList();

        Packet highPacket = new Packet0KeepAlive();
        OutboundQueueSystem.QueueState highQueueState = service.enqueuePacket(highPriority, lowPriority, highPacket, 0);
        Assert.assertEquals(1, highQueueState.getQueuedBytes());
        Assert.assertEquals(1, highPriority.size());
        Assert.assertEquals(0, lowPriority.size());

        Packet lowPacket = new Packet0KeepAlive();
        lowPacket.k = true;
        OutboundQueueSystem.QueueState lowQueueState = service.enqueuePacket(highPriority, lowPriority, lowPacket, highQueueState.getQueuedBytes());
        Assert.assertEquals(2, lowQueueState.getQueuedBytes());
        Assert.assertEquals(1, highPriority.size());
        Assert.assertEquals(1, lowPriority.size());
    }

    @Test
    public void drainingHighPriorityPacketKeepsLowPriorityDelay() throws Exception {
        OutboundQueueSystem service = OutboundQueueSystem.getInstance();
        List highPriority = new ArrayList();
        List lowPriority = new ArrayList();
        highPriority.add(new Packet0KeepAlive());

        DataOutputStream output = new DataOutputStream(new ByteArrayOutputStream());
        int[] outboundCounters = new int[256];
        OutboundQueueSystem.DrainResult result = service.drain(
                new Object(),
                highPriority,
                lowPriority,
                1,
                50,
                0,
                System.currentTimeMillis(),
                output,
                outboundCounters
        );

        Assert.assertTrue(result.wrotePacket());
        Assert.assertEquals(0, result.getQueuedBytes());
        Assert.assertEquals(50, result.getLowPriorityQueueDelay());
        Assert.assertEquals(1, outboundCounters[0]);
    }

    @Test
    public void lowPriorityDelayMustElapseBeforeDrain() throws Exception {
        OutboundQueueSystem service = OutboundQueueSystem.getInstance();
        List highPriority = new ArrayList();
        List lowPriority = new ArrayList();
        Packet lowPacket = new Packet0KeepAlive();
        lowPacket.k = true;
        lowPriority.add(lowPacket);

        DataOutputStream output = new DataOutputStream(new ByteArrayOutputStream());
        int[] outboundCounters = new int[256];
        OutboundQueueSystem.DrainResult delayedResult = service.drain(
                new Object(),
                highPriority,
                lowPriority,
                1,
                5,
                0,
                System.currentTimeMillis(),
                output,
                outboundCounters
        );
        Assert.assertFalse(delayedResult.wrotePacket());
        Assert.assertEquals(1, delayedResult.getQueuedBytes());
        Assert.assertEquals(4, delayedResult.getLowPriorityQueueDelay());
        Assert.assertEquals(1, lowPriority.size());
        Assert.assertEquals(0, outboundCounters[0]);

        OutboundQueueSystem.DrainResult drainedResult = service.drain(
                new Object(),
                highPriority,
                lowPriority,
                delayedResult.getQueuedBytes(),
                0,
                0,
                System.currentTimeMillis(),
                output,
                outboundCounters
        );
        Assert.assertTrue(drainedResult.wrotePacket());
        Assert.assertEquals(0, drainedResult.getQueuedBytes());
        Assert.assertEquals(0, drainedResult.getLowPriorityQueueDelay());
        Assert.assertEquals(0, lowPriority.size());
        Assert.assertEquals(1, outboundCounters[0]);
    }
}
