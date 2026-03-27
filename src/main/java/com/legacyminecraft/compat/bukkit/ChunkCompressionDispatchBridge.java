package com.legacyminecraft.compat.bukkit;

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

    public void sendPacket(Object player, Object packet) {
        try {
            Class<?> threadClass = Class.forName("org.bukkit.craftbukkit.ChunkCompressionThread");
            Class<?> entityPlayerClass = Class.forName("net.minecraft.server.EntityPlayer");
            Class<?> packetClass = Class.forName("net.minecraft.server.Packet");
            threadClass.getMethod("sendPacket", entityPlayerClass, packetClass).invoke(null, player, packet);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public int getPlayerQueueSize(Object player) {
        try {
            Class<?> threadClass = Class.forName("org.bukkit.craftbukkit.ChunkCompressionThread");
            Class<?> entityPlayerClass = Class.forName("net.minecraft.server.EntityPlayer");
            Object size = threadClass.getMethod("getPlayerQueueSize", entityPlayerClass).invoke(null, player);
            return size instanceof Number ? ((Number) size).intValue() : 0;
        } catch (ReflectiveOperationException ignored) {
            return 0;
        }
    }
}
