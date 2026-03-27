package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftChunk wrapper projection and rebinding glue.
 */
public final class ChunkWrapperProjectionBridgeBehaviour {
    private static final ChunkWrapperProjectionBridgeBehaviour INSTANCE = new ChunkWrapperProjectionBridgeBehaviour();

    private ChunkWrapperProjectionBridgeBehaviour() {
    }

    public static ChunkWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.craftbukkit.CraftChunk resolveCraftChunk(org.bukkit.Chunk chunk) {
        return (org.bukkit.craftbukkit.CraftChunk) chunk;
    }

    public void bindLoadedBukkitChunk(org.bukkit.craftbukkit.CraftWorld craftWorld, org.bukkit.Chunk requestedChunk) {
        org.bukkit.craftbukkit.CraftChunk loadedChunk = resolveCraftChunk(craftWorld.getChunkAt(requestedChunk.getX(), requestedChunk.getZ()));
        loadedChunk.getHandle().bukkitChunk = requestedChunk;
    }
}
