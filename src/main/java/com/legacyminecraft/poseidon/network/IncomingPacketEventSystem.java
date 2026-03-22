package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import org.bukkit.Server;
import org.bukkit.event.packet.PacketReceivedEvent;

/**
 * Canonical service for incoming packet event emission and cancellation checks.
 */
public final class IncomingPacketEventSystem {
    private static final IncomingPacketEventSystem INSTANCE = new IncomingPacketEventSystem();
    private final PacketEventPolicy eventPolicy = PacketEventPolicy.getInstance();

    private IncomingPacketEventSystem() {
    }

    public static IncomingPacketEventSystem getInstance() {
        return INSTANCE;
    }

    public boolean allowIncomingPacket(Server server, EntityPlayer player, Packet packet) {
        PacketReceivedEvent event = new PacketReceivedEvent(server.getPlayer(player.name), packet);
        server.getPluginManager().callEvent(event);
        return eventPolicy.isIncomingAllowed(event.isCancelled());
    }
}
