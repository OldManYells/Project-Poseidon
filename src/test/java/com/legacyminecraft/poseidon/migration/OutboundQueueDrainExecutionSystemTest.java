package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutboundQueueDrainExecutionSystem;
import com.legacyminecraft.poseidon.network.OutboundQueueSystem;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

public class OutboundQueueDrainExecutionSystemTest {
    private final OutboundQueueDrainExecutionSystem outboundQueueDrainExecutionSystem =
            OutboundQueueDrainExecutionSystem.getInstance();
    private final OutboundQueueSystem outboundQueueSystem = OutboundQueueSystem.getInstance();

    @Test
    public void executeAppliesDrainResultWhenWriteSucceeds() {
        ActionsCapture actionsCapture = new ActionsCapture();
        Object queueLock = new Object();
        List highPriorityQueue = new ArrayList();
        List lowPriorityQueue = new ArrayList();
        Packet0KeepAlive packet0KeepAlive = new Packet0KeepAlive();
        highPriorityQueue.add(packet0KeepAlive);
        int queuedBytes = packet0KeepAlive.a() + 1;

        OutboundQueueDrainExecutionSystem.DrainStepResult drainStepResult = outboundQueueDrainExecutionSystem.execute(
                queueLock,
                highPriorityQueue,
                lowPriorityQueue,
                queuedBytes,
                50,
                0,
                System.currentTimeMillis(),
                new DataOutputStream(new ByteArrayOutputStream()),
                new int[256],
                outboundQueueSystem,
                actionsCapture
        );

        Assert.assertTrue(drainStepResult.wrotePacket());
        Assert.assertEquals(0, drainStepResult.getQueuedBytes());
        Assert.assertEquals(50, drainStepResult.getLowPriorityQueueDelay());
        Assert.assertFalse(actionsCapture.exceptionHandled);
    }

    @Test
    public void executePreservesStateWhenDrainThrows() {
        ActionsCapture actionsCapture = new ActionsCapture();
        Object queueLock = new Object();
        List highPriorityQueue = new ArrayList();
        List lowPriorityQueue = new ArrayList();
        Packet0KeepAlive packet0KeepAlive = new Packet0KeepAlive();
        highPriorityQueue.add(packet0KeepAlive);
        int queuedBytes = packet0KeepAlive.a() + 1;
        int lowPriorityQueueDelay = 50;

        OutboundQueueDrainExecutionSystem.DrainStepResult drainStepResult = outboundQueueDrainExecutionSystem.execute(
                queueLock,
                highPriorityQueue,
                lowPriorityQueue,
                queuedBytes,
                lowPriorityQueueDelay,
                0,
                System.currentTimeMillis(),
                null,
                new int[256],
                outboundQueueSystem,
                actionsCapture
        );

        Assert.assertFalse(drainStepResult.wrotePacket());
        Assert.assertEquals(queuedBytes, drainStepResult.getQueuedBytes());
        Assert.assertEquals(lowPriorityQueueDelay, drainStepResult.getLowPriorityQueueDelay());
        Assert.assertTrue(actionsCapture.exceptionHandled);
    }

    private static final class ActionsCapture implements OutboundQueueDrainExecutionSystem.OutboundDrainActions {
        private boolean exceptionHandled;

        @Override
        public void handleException(Exception exception) {
            this.exceptionHandled = true;
        }
    }
}
