package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginConnectionLossExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginConnectionLossExecutionSystemTest {
    private final LoginConnectionLossExecutionSystem loginConnectionLossExecutionSystem = LoginConnectionLossExecutionSystem.getInstance();

    @Test
    public void executeReportsAndMarksComplete() {
        ActionCapture actionCapture = new ActionCapture();

        loginConnectionLossExecutionSystem.execute(actionCapture);

        Assert.assertTrue(actionCapture.reported);
        Assert.assertTrue(actionCapture.markedComplete);
    }

    private static final class ActionCapture implements LoginConnectionLossExecutionSystem.ConnectionLossActions {
        private boolean reported;
        private boolean markedComplete;

        @Override
        public void reportConnectionLost() {
            this.reported = true;
        }

        @Override
        public void markLoginComplete() {
            this.markedComplete = true;
        }
    }
}
