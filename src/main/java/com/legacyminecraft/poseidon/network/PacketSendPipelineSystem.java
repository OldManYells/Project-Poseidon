package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import org.bukkit.entity.Player;

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
            NetworkManager networkManager,
            EntityPlayer player,
            Player bukkitPlayer,
            Packet packet,
            boolean firePacketEvents
    ) {
        Packet filteredPacket = outgoingPacketEventSystem.filterOutgoingPacket(firePacketEvents, player, packet);
        if (filteredPacket == null) {
            return false;
        }

        outboundPacketDispatchSystem.dispatchOutgoingPacket(networkManager, player, bukkitPlayer, filteredPacket);
        return true;
    }
}
