package net.minecraft.server;

import com.legacyminecraft.poseidon.world.ChunkPositionBehaviour;

public class ChunkPosition extends com.legacyminecraft.poseidon.world.ChunkPosition {
    private static final ChunkPositionBehaviour CHUNK_POSITION_BEHAVIOUR = ChunkPositionBehaviour.getInstance();

    public final int x;
    public final int y;
    public final int z;

    public ChunkPosition(int i, int j, int k) {
        super(i, j, k);
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public boolean equals(Object object) {
        return CHUNK_POSITION_BEHAVIOUR.equals(this, object);
    }

    public int hashCode() {
        return CHUNK_POSITION_BEHAVIOUR.hashCode(this);
    }
}
