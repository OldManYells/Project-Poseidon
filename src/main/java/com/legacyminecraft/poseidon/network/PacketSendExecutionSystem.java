package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for packet send resolution and send-result execution.
 */
public final class PacketSendExecutionSystem {
    private static final PacketSendExecutionSystem INSTANCE = new PacketSendExecutionSystem();

    private PacketSendExecutionSystem() {
    }

    public static PacketSendExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(
            PacketSendResultResolver packetSendResultResolver,
            PacketSendResultExecutionSystem packetSendResultExecutionSystem,
            PacketSendResultExecutionSystem.PacketSendActions packetSendActions
    ) {
        boolean sent = packetSendResultResolver.resolve();
        packetSendResultExecutionSystem.executeResult(sent, packetSendActions);
    }

    public interface PacketSendResultResolver {
        boolean resolve();
    }
}
