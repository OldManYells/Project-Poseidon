package com.legacyminecraft.poseidon.network;


import java.util.logging.Logger;

/**
 * Canonical execution flow for unexpected packet protocol errors.
 */
public final class UnexpectedPacketProtocolErrorExecutionSystem {
    private static final UnexpectedPacketProtocolErrorExecutionSystem INSTANCE = new UnexpectedPacketProtocolErrorExecutionSystem();
    private final ProtocolErrorMessagePolicy protocolErrorMessagePolicy = ProtocolErrorMessagePolicy.getInstance();

    private UnexpectedPacketProtocolErrorExecutionSystem() {
    }

    public static UnexpectedPacketProtocolErrorExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Class handlerClass, Object packet, Logger logger, ProtocolErrorActions protocolErrorActions) {
        logger.warning(handlerClass + " wasn\'t prepared to deal with a " + packet.getClass());
        protocolErrorActions.disconnect(protocolErrorMessagePolicy.protocolErrorKickMessage());
    }

    public interface ProtocolErrorActions {
        void disconnect(String message);
    }
}
