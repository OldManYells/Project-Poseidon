package com.legacyminecraft.compat.bukkit;


import java.util.concurrent.ConcurrentMap;

/**
 * Canonical behavior for CraftChunk cache keys and entity/tile-entity wrapper collection.
 */
public final class CraftChunkAccessBehaviour {
    private static final CraftChunkAccessBehaviour INSTANCE = new CraftChunkAccessBehaviour();
    private static final NmsEntityProjectionBridgeBehaviour NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR =
            NmsEntityProjectionBridgeBehaviour.getInstance();

    private CraftChunkAccessBehaviour() {
    }

    public static CraftChunkAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public org.bukkit.block.Block resolveBlock(
            ConcurrentMap<Integer, org.bukkit.block.Block> blockCache,
            org.bukkit.craftbukkit.CraftChunk craftChunk,
            int chunkX,
            int chunkZ,
            int localX,
            int blockY,
            int localZ
    ) {
        int cacheKey = toCacheKey(localX, blockY, localZ);
        org.bukkit.block.Block cachedBlock = blockCache.get(cacheKey);
        if (cachedBlock != null) {
            return cachedBlock;
        }

        org.bukkit.block.Block createdBlock = new org.bukkit.craftbukkit.block.CraftBlock(
                craftChunk,
                (chunkX << 4) | (localX & 0xF),
                blockY & 0x7F,
                (chunkZ << 4) | (localZ & 0xF)
        );
        org.bukkit.block.Block existingBlock = blockCache.put(cacheKey, createdBlock);
        return existingBlock == null ? createdBlock : existingBlock;
    }

    public org.bukkit.entity.Entity[] collectEntities(net.minecraft.server.Chunk chunk) {
        int estimatedCount = 0;
        for (int sliceIndex = 0; sliceIndex < chunk.entitySlices.length; ++sliceIndex) {
            estimatedCount += chunk.entitySlices[sliceIndex].size();
        }

        org.bukkit.entity.Entity[] entities = new org.bukkit.entity.Entity[estimatedCount];
        int nextIndex = 0;
        for (int sliceIndex = 0; sliceIndex < chunk.entitySlices.length; ++sliceIndex) {
            Object[] sliceContents = chunk.entitySlices[sliceIndex].toArray();
            for (Object entry : sliceContents) {
                org.bukkit.entity.Entity bukkitEntity = NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR.resolveBukkitEntity(entry);
                if (bukkitEntity != null) {
                    entities[nextIndex++] = bukkitEntity;
                }
            }
        }

        if (nextIndex == entities.length) {
            return entities;
        }

        org.bukkit.entity.Entity[] trimmed = new org.bukkit.entity.Entity[nextIndex];
        System.arraycopy(entities, 0, trimmed, 0, nextIndex);
        return trimmed;
    }

    public org.bukkit.block.BlockState[] collectTileEntityStates(net.minecraft.server.Chunk chunk, net.minecraft.server.WorldServer worldServer) {
        org.bukkit.block.BlockState[] states = new org.bukkit.block.BlockState[chunk.tileEntities.size()];
        int nextIndex = 0;
        Object[] tileEntityKeys = chunk.tileEntities.keySet().toArray();
        for (Object key : tileEntityKeys) {
            if (!(key instanceof net.minecraft.server.ChunkPosition)) {
                continue;
            }
            net.minecraft.server.ChunkPosition chunkPosition = (net.minecraft.server.ChunkPosition) key;
            states[nextIndex++] = worldServer.getWorld()
                    .getBlockAt(
                            chunkPosition.x + (chunk.x << 4),
                            chunkPosition.y,
                            chunkPosition.z + (chunk.z << 4)
                    )
                    .getState();
        }

        if (nextIndex == states.length) {
            return states;
        }

        org.bukkit.block.BlockState[] trimmed = new org.bukkit.block.BlockState[nextIndex];
        System.arraycopy(states, 0, trimmed, 0, nextIndex);
        return trimmed;
    }

    private int toCacheKey(int localX, int blockY, int localZ) {
        return (localX & 0xF) << 11 | (localZ & 0xF) << 7 | (blockY & 0x7F);
    }
}
