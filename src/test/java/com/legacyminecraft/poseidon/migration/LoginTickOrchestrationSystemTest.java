package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginTickExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickOrchestrationSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickPolicy;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginTickOrchestrationSystemTest {
    private final LoginTickOrchestrationSystem loginTickOrchestrationSystem = LoginTickOrchestrationSystem.getInstance();
    private final LoginTickPolicy loginTickPolicy = LoginTickPolicy.getInstance();
    private final LoginTickExecutionSystem loginTickExecutionSystem = LoginTickExecutionSystem.getInstance();

    @Test
    public void executeProcessesDeferredLoginAndPollsWhenNotTimedOut() {
        TickActionsCapture tickActionsCapture = new TickActionsCapture();
        Packet1Login deferredLoginPacket = new Packet1Login("Player", 14, 0L, (byte) 0);

        int nextCounter = loginTickOrchestrationSystem.execute(
                deferredLoginPacket,
                10,
                600,
                loginTickPolicy,
                loginTickExecutionSystem,
                tickActionsCapture
        );

        Assert.assertEquals(11, nextCounter);
        Assert.assertTrue(tickActionsCapture.processedDeferredLogin);
        Assert.assertTrue(tickActionsCapture.polledNetwork);
        Assert.assertNull(tickActionsCapture.disconnectMessage);
    }

    @Test
    public void executeDisconnectsWhenTimedOut() {
        TickActionsCapture tickActionsCapture = new TickActionsCapture();

        int nextCounter = loginTickOrchestrationSystem.execute(
                null,
                600,
                600,
                loginTickPolicy,
                loginTickExecutionSystem,
                tickActionsCapture
        );

        Assert.assertEquals(601, nextCounter);
        Assert.assertFalse(tickActionsCapture.processedDeferredLogin);
        Assert.assertFalse(tickActionsCapture.polledNetwork);
        Assert.assertEquals("Took too long to log in", tickActionsCapture.disconnectMessage);
    }

    private static final class TickActionsCapture implements LoginTickExecutionSystem.TickActions {
        private boolean processedDeferredLogin;
        private boolean polledNetwork;
        private String disconnectMessage;

        @Override
        public void processDeferredLogin() {
            this.processedDeferredLogin = true;
        }

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }

        @Override
        public void pollNetwork() {
            this.polledNetwork = true;
        }
    }
}
