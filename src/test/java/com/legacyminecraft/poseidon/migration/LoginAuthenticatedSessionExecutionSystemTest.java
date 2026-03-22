package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginAuthenticatedSessionExecutionSystem;
import com.legacyminecraft.poseidon.auth.login.LoginTransitionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginAuthenticatedSessionExecutionSystemTest {
    private final LoginAuthenticatedSessionExecutionSystem loginAuthenticatedSessionExecutionSystem =
            LoginAuthenticatedSessionExecutionSystem.getInstance();

    @Test
    public void executeCompletesSessionAndAppliesCompletionState() {
        ActionCapture actionCapture = new ActionCapture();

        loginAuthenticatedSessionExecutionSystem.execute(actionCapture);

        Assert.assertTrue(actionCapture.completed);
        Assert.assertTrue(actionCapture.applied);
    }

    private static final class ActionCapture implements LoginAuthenticatedSessionExecutionSystem.CompletionActions {
        private boolean completed;
        private boolean applied;

        @Override
        public LoginTransitionSystem.CompletionResult completeAuthenticatedSession() {
            this.completed = true;
            return LoginTransitionSystem.CompletionResult.completed();
        }

        @Override
        public void applyCompletionState(LoginTransitionSystem.CompletionResult completionResult) {
            this.applied = completionResult != null && completionResult.shouldMarkLoginComplete();
        }
    }
}
