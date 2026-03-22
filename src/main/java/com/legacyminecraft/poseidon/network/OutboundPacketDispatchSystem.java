package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.compat.bukkit.ChunkCompressionDispatchBridge;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.NetworkManager;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet3Chat;
import net.minecraft.server.Packet6SpawnPosition;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Canonical service for outgoing packet dispatch behavior.
 */
public final class OutboundPacketDispatchSystem {
    private static final OutboundPacketDispatchSystem INSTANCE = new OutboundPacketDispatchSystem();
    private final ChunkCompressionDispatchBridge chunkCompressionDispatchBridge = ChunkCompressionDispatchBridge.getInstance();
    private final ChatTextWrapBehaviour chatTextWrapBehaviour = ChatTextWrapBehaviour.getInstance();

    private OutboundPacketDispatchSystem() {
    }

    public static OutboundPacketDispatchSystem getInstance() {
        return INSTANCE;
    }

    public void dispatchOutgoingPacket(NetworkManager networkManager, EntityPlayer entityPlayer, Player bukkitPlayer, Packet packet) {
        if (packet instanceof Packet6SpawnPosition) {
            Packet6SpawnPosition packet6 = (Packet6SpawnPosition) packet;
            entityPlayer.compassTarget = new Location(bukkitPlayer.getWorld(), packet6.x, packet6.y, packet6.z);
        } else if (packet instanceof Packet3Chat) {
            String message = ((Packet3Chat) packet).message;
            for (String line : chatTextWrapBehaviour.wrapText(message)) {
                networkManager.queue(new Packet3Chat(line));
            }
            return;
        } else if (isLowPriorityPacket(packet)) {
            chunkCompressionDispatchBridge.sendPacket(entityPlayer, packet);
            return;
        }

        networkManager.queue(packet);
    }

    public boolean isLowPriorityPacket(Packet packet) {
        return packet.k;
    }
}
