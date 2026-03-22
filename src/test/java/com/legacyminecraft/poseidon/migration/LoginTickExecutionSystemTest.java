package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginTickExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTickPolicy;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginTickExecutionSystemTest {
    private final LoginTickPolicy loginTickPolicy = LoginTickPolicy.getInstance();
    private final LoginTickExecutionSystem loginTickExecutionSystem = LoginTickExecutionSystem.getInstance();

    @Test
    public void applyTickDecisionProcessesDeferredLoginAndPollsWhenNotTimedOut() {
        LoginTickPolicy.TickDecision tickDecision =
                loginTickPolicy.evaluateTick(new Packet1Login(), 10, 600);
        TickActionCapture tickActionCapture = new TickActionCapture();

        int nextTimeoutCounter = loginTickExecutionSystem.applyTickDecision(tickDecision, tickActionCapture);

        Assert.assertTrue(tickActionCapture.processedDeferredLogin);
        Assert.assertFalse(tickActionCapture.disconnected);
        Assert.assertTrue(tickActionCapture.polledNetwork);
        Assert.assertEquals(11, nextTimeoutCounter);
    }

    @Test
    public void applyTickDecisionDisconnectsWhenTimeoutReached() {
        LoginTickPolicy.TickDecision tickDecision =
                loginTickPolicy.evaluateTick(null, 600, 600);
        TickActionCapture tickActionCapture = new TickActionCapture();

        int nextTimeoutCounter = loginTickExecutionSystem.applyTickDecision(tickDecision, tickActionCapture);

        Assert.assertFalse(tickActionCapture.processedDeferredLogin);
        Assert.assertTrue(tickActionCapture.disconnected);
        Assert.assertEquals("Took too long to log in", tickActionCapture.disconnectMessage);
        Assert.assertFalse(tickActionCapture.polledNetwork);
        Assert.assertEquals(601, nextTimeoutCounter);
    }

    private static final class TickActionCapture implements LoginTickExecutionSystem.TickActions {
        private boolean processedDeferredLogin;
        private boolean disconnected;
        private String disconnectMessage;
        private boolean polledNetwork;

        @Override
        public void processDeferredLogin() {
            this.processedDeferredLogin = true;
        }

        @Override
        public void disconnect(String message) {
            this.disconnected = true;
            this.disconnectMessage = message;
        }

        @Override
        public void pollNetwork() {
            this.polledNetwork = true;
        }
    }
}
