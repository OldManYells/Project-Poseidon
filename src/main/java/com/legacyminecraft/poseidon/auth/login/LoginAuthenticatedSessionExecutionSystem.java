package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for authenticated session completion.
 */
public final class LoginAuthenticatedSessionExecutionSystem {
    private static final LoginAuthenticatedSessionExecutionSystem INSTANCE = new LoginAuthenticatedSessionExecutionSystem();

    private LoginAuthenticatedSessionExecutionSystem() {
    }

    public static LoginAuthenticatedSessionExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(CompletionActions completionActions) {
        LoginTransitionSystem.CompletionResult completionResult = completionActions.completeAuthenticatedSession();
        completionActions.applyCompletionState(completionResult);
    }

    public interface CompletionActions {
        LoginTransitionSystem.CompletionResult completeAuthenticatedSession();

        void applyCompletionState(LoginTransitionSystem.CompletionResult completionResult);
    }
}
