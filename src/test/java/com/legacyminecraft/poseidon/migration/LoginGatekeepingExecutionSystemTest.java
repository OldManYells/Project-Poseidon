package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginGatekeepingExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginPacketGatekeepingPolicy;
import org.junit.Assert;
import org.junit.Test;

public class LoginGatekeepingExecutionSystemTest {
    private final LoginGatekeepingExecutionSystem system = LoginGatekeepingExecutionSystem.getInstance();

    @Test
    public void applyGatekeepingResultRejectDisconnectsAndStops() {
        LoginPacketGatekeepingPolicy.GatekeepingResult gatekeepingResult =
                LoginPacketGatekeepingPolicy.GatekeepingResult.reject("duplicate");
        GatekeepingActionCapture gatekeepingActionCapture = new GatekeepingActionCapture();

        boolean accepted = system.applyGatekeepingResult(gatekeepingResult, gatekeepingActionCapture);

        Assert.assertFalse(accepted);
        Assert.assertEquals("duplicate", gatekeepingActionCapture.disconnectMessage);
        Assert.assertFalse(gatekeepingActionCapture.loginPacketMarked);
        Assert.assertNull(gatekeepingActionCapture.username);
    }

    @Test
    public void applyGatekeepingResultAcceptMarksAndUpdatesUsername() {
        LoginPacketGatekeepingPolicy.GatekeepingResult gatekeepingResult =
                LoginPacketGatekeepingPolicy.GatekeepingResult.accept("Notch", null);
        GatekeepingActionCapture gatekeepingActionCapture = new GatekeepingActionCapture();

        boolean accepted = system.applyGatekeepingResult(gatekeepingResult, gatekeepingActionCapture);

        Assert.assertTrue(accepted);
        Assert.assertTrue(gatekeepingActionCapture.loginPacketMarked);
        Assert.assertEquals("Notch", gatekeepingActionCapture.username);
        Assert.assertNull(gatekeepingActionCapture.disconnectMessage);
    }

    @Test
    public void applyGatekeepingResultAcceptWithProtocolKickStillReturnsAcceptedAndDisconnects() {
        LoginPacketGatekeepingPolicy.GatekeepingResult gatekeepingResult =
                LoginPacketGatekeepingPolicy.GatekeepingResult.accept("Player", "outdated");
        GatekeepingActionCapture gatekeepingActionCapture = new GatekeepingActionCapture();

        boolean accepted = system.applyGatekeepingResult(gatekeepingResult, gatekeepingActionCapture);

        Assert.assertTrue(accepted);
        Assert.assertTrue(gatekeepingActionCapture.loginPacketMarked);
        Assert.assertEquals("Player", gatekeepingActionCapture.username);
        Assert.assertEquals("outdated", gatekeepingActionCapture.disconnectMessage);
    }

    private static final class GatekeepingActionCapture implements LoginGatekeepingExecutionSystem.GatekeepingActions {
        private boolean loginPacketMarked;
        private String username;
        private String disconnectMessage;

        @Override
        public void markLoginPacketReceived() {
            this.loginPacketMarked = true;
        }

        @Override
        public void updateUsername(String username) {
            this.username = username;
        }

        @Override
        public void disconnect(String message) {
            this.disconnectMessage = message;
        }
    }
}
