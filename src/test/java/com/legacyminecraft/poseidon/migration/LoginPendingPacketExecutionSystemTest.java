package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginPendingPacketExecutionSystem;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginPendingPacketExecutionSystemTest {
    private final LoginPendingPacketExecutionSystem loginPendingPacketExecutionSystem =
            LoginPendingPacketExecutionSystem.getInstance();

    @Test
    public void executeSetsRunsAndClearsPendingPacket() {
        PendingPacketCapture pendingPacketCapture = new PendingPacketCapture();
        ExecutionCapture executionCapture = new ExecutionCapture(false);
        Packet1Login packet1Login = new Packet1Login("Player", 14, 0L, (byte) 0);

        loginPendingPacketExecutionSystem.execute(packet1Login, pendingPacketCapture, executionCapture);

        Assert.assertSame(packet1Login, pendingPacketCapture.lastSetPacket);
        Assert.assertTrue(pendingPacketCapture.cleared);
        Assert.assertTrue(executionCapture.executed);
    }

    @Test
    public void executeClearsPendingPacketWhenExecutionThrows() {
        PendingPacketCapture pendingPacketCapture = new PendingPacketCapture();
        ExecutionCapture executionCapture = new ExecutionCapture(true);
        Packet1Login packet1Login = new Packet1Login("Player", 14, 0L, (byte) 0);

        try {
            loginPendingPacketExecutionSystem.execute(packet1Login, pendingPacketCapture, executionCapture);
            Assert.fail("Expected failure");
        } catch (IllegalStateException expected) {
            Assert.assertEquals("execution failed", expected.getMessage());
        }

        Assert.assertSame(packet1Login, pendingPacketCapture.lastSetPacket);
        Assert.assertTrue(pendingPacketCapture.cleared);
        Assert.assertTrue(executionCapture.executed);
    }

    private static final class PendingPacketCapture implements LoginPendingPacketExecutionSystem.PendingPacketState {
        private Packet1Login lastSetPacket;
        private boolean cleared;

        @Override
        public void set(Packet1Login loginPacket) {
            this.lastSetPacket = loginPacket;
        }

        @Override
        public void clear() {
            this.cleared = true;
        }
    }

    private static final class ExecutionCapture implements Runnable {
        private final boolean shouldThrow;
        private boolean executed;

        private ExecutionCapture(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public void run() {
            this.executed = true;
            if (this.shouldThrow) {
                throw new IllegalStateException("execution failed");
            }
        }
    }
}
