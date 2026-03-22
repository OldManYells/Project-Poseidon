package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionHeartbeatExecutionSystem;
import com.legacyminecraft.poseidon.network.ConnectionHeartbeatSystem;
import net.minecraft.server.Packet;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionHeartbeatExecutionSystemTest {
    private final ConnectionHeartbeatExecutionSystem system = ConnectionHeartbeatExecutionSystem.getInstance();
    private final ConnectionHeartbeatSystem heartbeatSystem = ConnectionHeartbeatSystem.getInstance();

    @Test
    public void applyHeartbeatResetsMovementFlagAndPollsAndSendsKeepAlive() {
        ActionState actionState = new ActionState();

        boolean updatedMovementFlag = system.applyHeartbeat(
                30,
                0,
                20,
                true,
                heartbeatSystem,
                actionState
        );

        Assert.assertFalse(updatedMovementFlag);
        Assert.assertTrue(actionState.polled);
        Assert.assertTrue(actionState.sentPacket);
    }

    private static final class ActionState implements ConnectionHeartbeatExecutionSystem.HeartbeatActions {
        private boolean polled;
        private boolean sentPacket;

        public void pollNetwork() {
            polled = true;
        }

        public void sendPacket(Packet packet) {
            sentPacket = packet != null;
        }
    }
}
