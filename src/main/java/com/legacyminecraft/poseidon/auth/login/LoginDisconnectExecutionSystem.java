package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for explicit login disconnect requests.
 */
public final class LoginDisconnectExecutionSystem {
    private static final LoginDisconnectExecutionSystem INSTANCE = new LoginDisconnectExecutionSystem();

    private LoginDisconnectExecutionSystem() {
    }

    public static LoginDisconnectExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(String disconnectMessage, DisconnectActions disconnectActions) {
        disconnectActions.disconnect(disconnectMessage);
        disconnectActions.markLoginComplete();
    }

    public interface DisconnectActions {
        void disconnect(String message);

        void markLoginComplete();
    }
}
