package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSendExecutionSystem;
import com.legacyminecraft.poseidon.network.PacketSendResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class PacketSendExecutionSystemTest {
    private final PacketSendExecutionSystem packetSendExecutionSystem = PacketSendExecutionSystem.getInstance();
    private final PacketSendResultExecutionSystem packetSendResultExecutionSystem =
            PacketSendResultExecutionSystem.getInstance();

    @Test
    public void executeMarksPacketSentWhenResolverReturnsTrue() {
        ResolverCapture resolverCapture = new ResolverCapture(true);
        ActionsCapture actionsCapture = new ActionsCapture();

        packetSendExecutionSystem.execute(resolverCapture, packetSendResultExecutionSystem, actionsCapture);

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertTrue(actionsCapture.markedPacketSent);
    }

    @Test
    public void executeDoesNotMarkPacketSentWhenResolverReturnsFalse() {
        ResolverCapture resolverCapture = new ResolverCapture(false);
        ActionsCapture actionsCapture = new ActionsCapture();

        packetSendExecutionSystem.execute(resolverCapture, packetSendResultExecutionSystem, actionsCapture);

        Assert.assertEquals(1, resolverCapture.resolveCalls);
        Assert.assertFalse(actionsCapture.markedPacketSent);
    }

    private static final class ResolverCapture implements PacketSendExecutionSystem.PacketSendResultResolver {
        private final boolean sendResult;
        private int resolveCalls;

        private ResolverCapture(boolean sendResult) {
            this.sendResult = sendResult;
        }

        @Override
        public boolean resolve() {
            this.resolveCalls++;
            return this.sendResult;
        }
    }

    private static final class ActionsCapture implements PacketSendResultExecutionSystem.PacketSendActions {
        private boolean markedPacketSent;

        @Override
        public void markPacketSent() {
            this.markedPacketSent = true;
        }
    }
}
