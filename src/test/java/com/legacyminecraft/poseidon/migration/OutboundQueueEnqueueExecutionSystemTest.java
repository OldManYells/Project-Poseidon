package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutboundQueueEnqueueExecutionSystem;
import com.legacyminecraft.poseidon.network.OutboundQueueSystem;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class OutboundQueueEnqueueExecutionSystemTest {
    private final OutboundQueueEnqueueExecutionSystem outboundQueueEnqueueExecutionSystem =
            OutboundQueueEnqueueExecutionSystem.getInstance();
    private final OutboundQueueSystem outboundQueueSystem = OutboundQueueSystem.getInstance();

    @Test
    public void executeEnqueuesPacketAndReturnsUpdatedQueuedBytesWhenOpen() {
        List highPriorityQueue = new ArrayList();
        List lowPriorityQueue = new ArrayList();
        Packet0KeepAlive packet0KeepAlive = new Packet0KeepAlive();

        OutboundQueueEnqueueExecutionSystem.EnqueueStepResult enqueueStepResult =
                outboundQueueEnqueueExecutionSystem.execute(
                        false,
                        new Object(),
                        highPriorityQueue,
                        lowPriorityQueue,
                        packet0KeepAlive,
                        0,
                        outboundQueueSystem
                );

        Assert.assertTrue(enqueueStepResult.isEnqueued());
        Assert.assertEquals(packet0KeepAlive.a() + 1, enqueueStepResult.getQueuedBytes());
        Assert.assertEquals(1, highPriorityQueue.size());
        Assert.assertTrue(lowPriorityQueue.isEmpty());
    }

    @Test
    public void executeSkipsEnqueueWhenShuttingDown() {
        List highPriorityQueue = new ArrayList();
        List lowPriorityQueue = new ArrayList();
        Packet0KeepAlive packet0KeepAlive = new Packet0KeepAlive();

        OutboundQueueEnqueueExecutionSystem.EnqueueStepResult enqueueStepResult =
                outboundQueueEnqueueExecutionSystem.execute(
                        true,
                        new Object(),
                        highPriorityQueue,
                        lowPriorityQueue,
                        packet0KeepAlive,
                        99,
                        outboundQueueSystem
                );

        Assert.assertFalse(enqueueStepResult.isEnqueued());
        Assert.assertEquals(99, enqueueStepResult.getQueuedBytes());
        Assert.assertTrue(highPriorityQueue.isEmpty());
        Assert.assertTrue(lowPriorityQueue.isEmpty());
    }
}
