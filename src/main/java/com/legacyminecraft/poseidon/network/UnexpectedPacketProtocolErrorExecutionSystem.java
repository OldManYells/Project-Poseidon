package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet;

import java.util.logging.Logger;

/**
 * Canonical execution flow for unexpected packet protocol errors.
 */
public final class UnexpectedPacketProtocolErrorExecutionSystem {
    private static final UnexpectedPacketProtocolErrorExecutionSystem INSTANCE = new UnexpectedPacketProtocolErrorExecutionSystem();
    private static final String PROTOCOL_ERROR_KICK_MESSAGE = "Protocol error, unexpected packet";

    private UnexpectedPacketProtocolErrorExecutionSystem() {
    }

    public static UnexpectedPacketProtocolErrorExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Class handlerClass, Packet packet, Logger logger, ProtocolErrorActions protocolErrorActions) {
        logger.warning(handlerClass + " wasn\'t prepared to deal with a " + packet.getClass());
        protocolErrorActions.disconnect(PROTOCOL_ERROR_KICK_MESSAGE);
    }

    public interface ProtocolErrorActions {
        void disconnect(String message);
    }
}
