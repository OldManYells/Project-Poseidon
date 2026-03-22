package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSendResultExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class PacketSendResultExecutionSystemTest {
    private final PacketSendResultExecutionSystem packetSendResultExecutionSystem = PacketSendResultExecutionSystem.getInstance();

    @Test
    public void executeResultMarksWhenSent() {
        ActionCapture actionCapture = new ActionCapture();

        packetSendResultExecutionSystem.executeResult(true, actionCapture);

        Assert.assertTrue(actionCapture.marked);
    }

    @Test
    public void executeResultSkipsMarkWhenNotSent() {
        ActionCapture actionCapture = new ActionCapture();

        packetSendResultExecutionSystem.executeResult(false, actionCapture);

        Assert.assertFalse(actionCapture.marked);
    }

    private static final class ActionCapture implements PacketSendResultExecutionSystem.PacketSendActions {
        private boolean marked;

        @Override
        public void markPacketSent() {
            this.marked = true;
        }
    }
}
