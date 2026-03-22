package com.legacyminecraft.poseidon.world;

import net.minecraft.server.ChunkPosition;

public final class ChunkPositionBehaviour {
    private static final ChunkPositionBehaviour INSTANCE = new ChunkPositionBehaviour();

    private ChunkPositionBehaviour() {
    }

    public static ChunkPositionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equals(ChunkPosition current, Object other) {
        if (!(other instanceof ChunkPosition)) {
            return false;
        }

        ChunkPosition chunkposition = (ChunkPosition) other;
        return chunkposition.x == current.x && chunkposition.y == current.y && chunkposition.z == current.z;
    }

    public int hashCode(ChunkPosition position) {
        return position.x * 8976890 + position.y * 981131 + position.z;
    }
}
