package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionLossExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionLossExecutionSystemTest {
    private final ConnectionLossExecutionSystem connectionLossExecutionSystem = ConnectionLossExecutionSystem.getInstance();

    @Test
    public void executeConnectionLossSkipsReporterWhenAlreadyDisconnected() {
        ActionCapture actionCapture = new ActionCapture(true);

        boolean disconnected = connectionLossExecutionSystem.executeConnectionLoss(true, actionCapture);

        Assert.assertTrue(disconnected);
        Assert.assertFalse(actionCapture.called);
    }

    @Test
    public void executeConnectionLossUsesReporterWhenConnected() {
        ActionCapture actionCapture = new ActionCapture(true);

        boolean disconnected = connectionLossExecutionSystem.executeConnectionLoss(false, actionCapture);

        Assert.assertTrue(disconnected);
        Assert.assertTrue(actionCapture.called);
    }

    @Test
    public void executeConnectionLossOverloadShortCircuitsWhenAlreadyDisconnected() {
        boolean disconnected = connectionLossExecutionSystem.executeConnectionLoss(
                true,
                null,
                null,
                null,
                "reason",
                java.util.logging.Logger.getLogger("test")
        );

        Assert.assertTrue(disconnected);
    }

    private static final class ActionCapture implements ConnectionLossExecutionSystem.ConnectionLossActions {
        private final boolean disconnectResult;
        private boolean called;

        private ActionCapture(boolean disconnectResult) {
            this.disconnectResult = disconnectResult;
        }

        @Override
        public boolean reportAndDisconnect() {
            this.called = true;
            return disconnectResult;
        }
    }
}
