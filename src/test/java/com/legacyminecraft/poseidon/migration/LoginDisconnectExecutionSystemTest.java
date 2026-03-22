package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginDisconnectExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginDisconnectExecutionSystemTest {
    private final LoginDisconnectExecutionSystem loginDisconnectExecutionSystem = LoginDisconnectExecutionSystem.getInstance();

    @Test
    public void executeForwardsMessageAndMarksComplete() {
        ActionCapture actionCapture = new ActionCapture();

        loginDisconnectExecutionSystem.execute("disconnect-message", actionCapture);

        Assert.assertEquals("disconnect-message", actionCapture.message);
        Assert.assertTrue(actionCapture.markedComplete);
    }

    private static final class ActionCapture implements LoginDisconnectExecutionSystem.DisconnectActions {
        private String message;
        private boolean markedComplete;

        @Override
        public void disconnect(String message) {
            this.message = message;
        }

        @Override
        public void markLoginComplete() {
            this.markedComplete = true;
        }
    }
}
