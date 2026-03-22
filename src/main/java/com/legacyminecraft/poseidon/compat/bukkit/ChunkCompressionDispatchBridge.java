package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet;
import org.bukkit.craftbukkit.ChunkCompressionThread;

/**
 * Canonical bridge for delegating chunk-compression queue operations to the CraftBukkit compat thread.
 */
public final class ChunkCompressionDispatchBridge {
    private static final ChunkCompressionDispatchBridge INSTANCE = new ChunkCompressionDispatchBridge();

    private ChunkCompressionDispatchBridge() {
    }

    public static ChunkCompressionDispatchBridge getInstance() {
        return INSTANCE;
    }

    public void sendPacket(EntityPlayer player, Packet packet) {
        ChunkCompressionThread.sendPacket(player, packet);
    }

    public int getPlayerQueueSize(EntityPlayer player) {
        return ChunkCompressionThread.getPlayerQueueSize(player);
    }
}

