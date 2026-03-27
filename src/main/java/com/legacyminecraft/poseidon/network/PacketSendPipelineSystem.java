package com.legacyminecraft.poseidon.network;


/**
 * Canonical pipeline for outgoing packet filtering and dispatch.
 */
public final class PacketSendPipelineSystem {
    private static final PacketSendPipelineSystem INSTANCE = new PacketSendPipelineSystem();
    private final OutgoingPacketEventSystem outgoingPacketEventSystem = OutgoingPacketEventSystem.getInstance();
    private final OutboundPacketDispatchSystem outboundPacketDispatchSystem = OutboundPacketDispatchSystem.getInstance();

    private PacketSendPipelineSystem() {
    }

    public static PacketSendPipelineSystem getInstance() {
        return INSTANCE;
    }

    public boolean sendPacket(
            Object networkManager,
            Object player,
            Object bukkitPlayer,
            Object packet,
            boolean firePacketEvents
    ) {
        Object filteredPacket = outgoingPacketEventSystem.filterOutgoingPacket(firePacketEvents, player, packet);
        if (filteredPacket == null) {
            return false;
        }

        outboundPacketDispatchSystem.dispatchOutgoingPacket(networkManager, player, bukkitPlayer, filteredPacket);
        return true;
    }
}
