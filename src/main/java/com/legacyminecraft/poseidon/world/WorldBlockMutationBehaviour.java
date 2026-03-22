package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Chunk;
import net.minecraft.server.World;

/**
 * Canonical behaviour for raw world block mutation paths.
 */
public final class WorldBlockMutationBehaviour {
    private static final WorldBlockMutationBehaviour INSTANCE = new WorldBlockMutationBehaviour();
    private static final WorldBlockQueryBehaviour WORLD_BLOCK_QUERY_BEHAVIOUR = WorldBlockQueryBehaviour.getInstance();

    private WorldBlockMutationBehaviour() {
    }

    public static WorldBlockMutationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean setRawTypeIdAndData(World world, int x, int y, int z, int typeId, int data) {
        if (!WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinWorldBounds(x, z) || !WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinBuildHeight(y)) {
            return false;
        }

        Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        return chunk.a(x & 15, y, z & 15, typeId, data);
    }

    public boolean setRawTypeId(World world, int x, int y, int z, int typeId) {
        if (!WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinWorldBounds(x, z) || !WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinBuildHeight(y)) {
            return false;
        }

        Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        return chunk.a(x & 15, y, z & 15, typeId);
    }

    public boolean setRawData(World world, int x, int y, int z, int data) {
        if (!WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinWorldBounds(x, z) || !WORLD_BLOCK_QUERY_BEHAVIOUR.isWithinBuildHeight(y)) {
            return false;
        }

        Chunk chunk = world.getChunkAt(x >> 4, z >> 4);
        chunk.b(x & 15, y, z & 15, data);
        return true;
    }
}
