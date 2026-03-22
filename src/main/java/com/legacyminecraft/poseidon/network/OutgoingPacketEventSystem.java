package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.event.PlayerSendPacketEvent;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import org.bukkit.Bukkit;

/**
 * Canonical service for outgoing packet event emission and packet substitution/cancellation.
 */
public final class OutgoingPacketEventSystem {
    private static final OutgoingPacketEventSystem INSTANCE = new OutgoingPacketEventSystem();
    private final PacketEventPolicy eventPolicy = PacketEventPolicy.getInstance();

    private OutgoingPacketEventSystem() {
    }

    public static OutgoingPacketEventSystem getInstance() {
        return INSTANCE;
    }

    public Packet filterOutgoingPacket(boolean firePacketEvents, EntityPlayer player, Packet packet) {
        if (eventPolicy.shouldDropOutgoingPacket(packet)) {
            return null;
        }
        if (eventPolicy.shouldBypassOutgoingEventDispatch(firePacketEvents)) {
            return packet;
        }

        PlayerSendPacketEvent event = new PlayerSendPacketEvent(player.name, packet);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled()) {
            return null;
        }
        return event.getPacket();
    }
}
