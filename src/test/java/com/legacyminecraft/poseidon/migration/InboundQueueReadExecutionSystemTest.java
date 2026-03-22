package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.InboundQueueReadExecutionSystem;
import com.legacyminecraft.poseidon.network.InboundQueueReadSystem;
import net.minecraft.server.NetHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class InboundQueueReadExecutionSystemTest {
    private final InboundQueueReadExecutionSystem inboundQueueReadExecutionSystem =
            InboundQueueReadExecutionSystem.getInstance();
    private final InboundQueueReadSystem inboundQueueReadSystem = InboundQueueReadSystem.getInstance();

    @Test
    public void executeTriggersEndOfStreamDisconnectWithoutReportingException() {
        ActionsCapture actionsCapture = new ActionsCapture();
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(new byte[0]));
        List inboundQueue = new ArrayList();
        int[] packetCounters = new int[256];

        InboundQueueReadExecutionSystem.ReadStepResult readStepResult = inboundQueueReadExecutionSystem.execute(
                input,
                new NetHandlerStub(true),
                packetCounters,
                inboundQueue,
                inboundQueueReadSystem,
                actionsCapture
        );

        Assert.assertFalse(readStepResult.isPacketQueued());
        Assert.assertTrue(actionsCapture.disconnectEndOfStreamCalled);
        Assert.assertFalse(actionsCapture.exceptionHandled);
        Assert.assertTrue(inboundQueue.isEmpty());
    }

    @Test
    public void executeHandlesExceptionAndReturnsNoPacketQueued() {
        ActionsCapture actionsCapture = new ActionsCapture();

        InboundQueueReadExecutionSystem.ReadStepResult readStepResult = inboundQueueReadExecutionSystem.execute(
                null,
                new NetHandlerStub(true),
                new int[256],
                new ArrayList(),
                inboundQueueReadSystem,
                actionsCapture
        );

        Assert.assertFalse(readStepResult.isPacketQueued());
        Assert.assertFalse(actionsCapture.disconnectEndOfStreamCalled);
        Assert.assertTrue(actionsCapture.exceptionHandled);
    }

    private static final class ActionsCapture implements InboundQueueReadExecutionSystem.InboundReadActions {
        private boolean disconnectEndOfStreamCalled;
        private boolean exceptionHandled;

        @Override
        public void disconnectEndOfStream() {
            this.disconnectEndOfStreamCalled = true;
        }

        @Override
        public void handleException(Exception exception) {
            this.exceptionHandled = true;
        }
    }

    private static final class NetHandlerStub extends NetHandler {
        private final boolean serverSide;

        private NetHandlerStub(boolean serverSide) {
            this.serverSide = serverSide;
        }

        @Override
        public boolean c() {
            return this.serverSide;
        }
    }
}
