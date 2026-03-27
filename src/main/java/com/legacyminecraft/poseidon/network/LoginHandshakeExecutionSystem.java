package com.legacyminecraft.poseidon.network;


import java.util.Random;

/**
 * Canonical execution flow for login handshake response dispatch.
 */
public final class LoginHandshakeExecutionSystem {
    private static final LoginHandshakeExecutionSystem INSTANCE = new LoginHandshakeExecutionSystem();

    private LoginHandshakeExecutionSystem() {
    }

    public static LoginHandshakeExecutionSystem getInstance() {
        return INSTANCE;
    }

    public String executeHandshake(
            boolean onlineMode,
            String existingServerId,
            Random random,
            LoginHandshakePacketHandler loginHandshakePacketHandler,
            HandshakeActions handshakeActions
    ) {
        LoginHandshakePacketHandler.HandshakeDecision handshakeDecision =
                loginHandshakePacketHandler.createHandshakeDecision(onlineMode, existingServerId, random);
        handshakeActions.queueResponsePacket(handshakeDecision.getResponsePacket());
        return handshakeDecision.getServerId();
    }

    public interface HandshakeActions {
        void queueResponsePacket(Object responsePacket);
    }
}
