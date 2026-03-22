package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for applying login completion state to handlers.
 */
public final class LoginCompletionStateApplySystem {
    private static final LoginCompletionStateApplySystem INSTANCE = new LoginCompletionStateApplySystem();

    private LoginCompletionStateApplySystem() {
    }

    public static LoginCompletionStateApplySystem getInstance() {
        return INSTANCE;
    }

    public void applyCompletionState(
            LoginTransitionService.CompletionResult completionResult,
            CompletionStateSink completionStateSink
    ) {
        completionStateSink.markLoginComplete(completionResult.shouldMarkLoginComplete());
    }

    public void applyCompletionState(
            LoginTransitionSystem.CompletionResult completionResult,
            CompletionStateSink completionStateSink
    ) {
        completionStateSink.markLoginComplete(completionResult.shouldMarkLoginComplete());
    }

    public interface CompletionStateSink {
        void markLoginComplete(boolean loginComplete);
    }
}
