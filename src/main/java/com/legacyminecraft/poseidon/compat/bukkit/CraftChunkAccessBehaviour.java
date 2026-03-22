package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ChunkPosition;
import net.minecraft.server.WorldServer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.craftbukkit.block.CraftBlock;
import org.bukkit.entity.Entity;

import java.util.concurrent.ConcurrentMap;

/**
 * Canonical behavior for CraftChunk cache keys and entity/tile-entity wrapper collection.
 */
public final class CraftChunkAccessBehaviour {
    private static final CraftChunkAccessBehaviour INSTANCE = new CraftChunkAccessBehaviour();

    private CraftChunkAccessBehaviour() {
    }

    public static CraftChunkAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public Block resolveBlock(
            ConcurrentMap<Integer, Block> blockCache,
            CraftChunk craftChunk,
            int chunkX,
            int chunkZ,
            int localX,
            int blockY,
            int localZ
    ) {
        int cacheKey = toCacheKey(localX, blockY, localZ);
        Block cachedBlock = blockCache.get(cacheKey);
        if (cachedBlock != null) {
            return cachedBlock;
        }

        Block createdBlock = new CraftBlock(
                craftChunk,
                (chunkX << 4) | (localX & 0xF),
                blockY & 0x7F,
                (chunkZ << 4) | (localZ & 0xF)
        );
        Block existingBlock = blockCache.put(cacheKey, createdBlock);
        return existingBlock == null ? createdBlock : existingBlock;
    }

    public Entity[] collectEntities(net.minecraft.server.Chunk chunk) {
        int estimatedCount = 0;
        for (int sliceIndex = 0; sliceIndex < chunk.entitySlices.length; ++sliceIndex) {
            estimatedCount += chunk.entitySlices[sliceIndex].size();
        }

        Entity[] entities = new Entity[estimatedCount];
        int nextIndex = 0;
        for (int sliceIndex = 0; sliceIndex < chunk.entitySlices.length; ++sliceIndex) {
            Object[] sliceContents = chunk.entitySlices[sliceIndex].toArray();
            for (Object entry : sliceContents) {
                if (!(entry instanceof net.minecraft.server.Entity)) {
                    continue;
                }
                entities[nextIndex++] = ((net.minecraft.server.Entity) entry).getBukkitEntity();
            }
        }

        if (nextIndex == entities.length) {
            return entities;
        }

        Entity[] trimmed = new Entity[nextIndex];
        System.arraycopy(entities, 0, trimmed, 0, nextIndex);
        return trimmed;
    }

    public BlockState[] collectTileEntityStates(net.minecraft.server.Chunk chunk, WorldServer worldServer) {
        BlockState[] states = new BlockState[chunk.tileEntities.size()];
        int nextIndex = 0;
        Object[] tileEntityKeys = chunk.tileEntities.keySet().toArray();
        for (Object key : tileEntityKeys) {
            if (!(key instanceof ChunkPosition)) {
                continue;
            }
            ChunkPosition chunkPosition = (ChunkPosition) key;
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

        BlockState[] trimmed = new BlockState[nextIndex];
        System.arraycopy(states, 0, trimmed, 0, nextIndex);
        return trimmed;
    }

    private int toCacheKey(int localX, int blockY, int localZ) {
        return (localX & 0xF) << 11 | (localZ & 0xF) << 7 | (blockY & 0x7F);
    }
}

