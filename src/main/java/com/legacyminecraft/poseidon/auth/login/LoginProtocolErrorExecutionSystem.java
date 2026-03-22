package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for login protocol error callbacks.
 */
public final class LoginProtocolErrorExecutionSystem {
    private static final LoginProtocolErrorExecutionSystem INSTANCE = new LoginProtocolErrorExecutionSystem();

    private LoginProtocolErrorExecutionSystem() {
    }

    public static LoginProtocolErrorExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(String protocolErrorMessage, ProtocolErrorActions protocolErrorActions) {
        protocolErrorActions.disconnect(protocolErrorMessage);
    }

    public interface ProtocolErrorActions {
        void disconnect(String message);
    }
}
