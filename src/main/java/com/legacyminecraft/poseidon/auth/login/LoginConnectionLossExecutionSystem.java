package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for login connection-loss callbacks.
 */
public final class LoginConnectionLossExecutionSystem {
    private static final LoginConnectionLossExecutionSystem INSTANCE = new LoginConnectionLossExecutionSystem();

    private LoginConnectionLossExecutionSystem() {
    }

    public static LoginConnectionLossExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(ConnectionLossActions connectionLossActions) {
        connectionLossActions.reportConnectionLost();
        connectionLossActions.markLoginComplete();
    }

    public interface ConnectionLossActions {
        void reportConnectionLost();

        void markLoginComplete();
    }
}
