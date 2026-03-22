package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginFlowStartExecutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginFlowStartExecutionSystemTest {
    private final LoginFlowStartExecutionSystem loginFlowStartExecutionSystem = LoginFlowStartExecutionSystem.getInstance();

    @Test
    public void executeStartsLoginFlowAction() {
        ActionCapture actionCapture = new ActionCapture();

        loginFlowStartExecutionSystem.execute(actionCapture);

        Assert.assertTrue(actionCapture.started);
    }

    private static final class ActionCapture implements LoginFlowStartExecutionSystem.LoginFlowActions {
        private boolean started;

        @Override
        public void startLoginFlow() {
            this.started = true;
        }
    }
}
