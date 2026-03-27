package com.legacyminecraft.compat.bukkit;

import org.bukkit.Chunk;
import org.bukkit.craftbukkit.CraftChunk;

/**
 * Canonical behaviour for Bukkit-compat chunk wrapper creation and slice cleanup checks.
 */
public final class ChunkEntitySliceCleanupBehaviour {
    private static final ChunkEntitySliceCleanupBehaviour INSTANCE = new ChunkEntitySliceCleanupBehaviour();

    private ChunkEntitySliceCleanupBehaviour() {
    }

    public static ChunkEntitySliceCleanupBehaviour getInstance() {
        return INSTANCE;
    }

    public Chunk createBukkitChunk(Object chunk) {
        return new CraftChunk((net.minecraft.server.Chunk) chunk);
    }

    public boolean shouldRemoveCrossChunkPlayerEntity(Entity entity, int chunkX, int chunkZ) {
        if (!(entity instanceof EntityPlayer)) {
            return false;
        }
        int entityChunkX = Location.locToBlock(entity.locX) >> 4;
        int entityChunkZ = Location.locToBlock(entity.locZ) >> 4;
        return entityChunkX != chunkX || entityChunkZ != chunkZ;
    }
}
