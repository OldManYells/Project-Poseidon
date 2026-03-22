package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginCompletionStateApplySystem;
import com.legacyminecraft.poseidon.auth.login.LoginTransitionSystem;
import org.junit.Assert;
import org.junit.Test;

public class LoginCompletionStateApplySystemTest {
    private final LoginCompletionStateApplySystem loginCompletionStateApplySystem = LoginCompletionStateApplySystem.getInstance();

    @Test
    public void applyCompletionStateForwardsCompletionFlag() {
        LoginTransitionSystem.CompletionResult completionResult = LoginTransitionSystem.CompletionResult.completed();
        CompletionStateCapture completionStateCapture = new CompletionStateCapture();

        loginCompletionStateApplySystem.applyCompletionState(completionResult, completionStateCapture);

        Assert.assertTrue(completionStateCapture.loginComplete);
    }

    private static final class CompletionStateCapture implements LoginCompletionStateApplySystem.CompletionStateSink {
        private boolean loginComplete;

        @Override
        public void markLoginComplete(boolean loginComplete) {
            this.loginComplete = loginComplete;
        }
    }
}
