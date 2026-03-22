package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution flow for outgoing packet send results.
 */
public final class PacketSendResultExecutionSystem {
    private static final PacketSendResultExecutionSystem INSTANCE = new PacketSendResultExecutionSystem();

    private PacketSendResultExecutionSystem() {
    }

    public static PacketSendResultExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void executeResult(boolean sent, PacketSendActions packetSendActions) {
        if (sent) {
            packetSendActions.markPacketSent();
        }
    }

    public interface PacketSendActions {
        void markPacketSent();
    }
}
