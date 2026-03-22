package com.legacyminecraft.poseidon.auth.login;

/**
 * Canonical execution flow for login gatekeeping decisions.
 */
public final class LoginGatekeepingExecutionSystem {
    private static final LoginGatekeepingExecutionSystem INSTANCE = new LoginGatekeepingExecutionSystem();

    private LoginGatekeepingExecutionSystem() {
    }

    public static LoginGatekeepingExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean applyGatekeepingResult(
            LoginPacketGatekeepingPolicy.GatekeepingResult gatekeepingResult,
            GatekeepingActions gatekeepingActions
    ) {
        if (!gatekeepingResult.isAccepted()) {
            gatekeepingActions.disconnect(gatekeepingResult.getDisconnectMessage());
            return false;
        }

        gatekeepingActions.markLoginPacketReceived();
        gatekeepingActions.updateUsername(gatekeepingResult.getUsername());

        String protocolKickMessage = gatekeepingResult.getProtocolKickMessage();
        if (protocolKickMessage != null) {
            gatekeepingActions.disconnect(protocolKickMessage);
        }

        return true;
    }

    public interface GatekeepingActions {
        void markLoginPacketReceived();

        void updateUsername(String username);

        void disconnect(String message);
    }
}
