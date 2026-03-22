package net.minecraft.server;

import com.legacyminecraft.poseidon.world.CoordinateMathBehaviour;

public class ChunkCoordIntPair {
    private static final CoordinateMathBehaviour COORDINATE_MATH_BEHAVIOUR = CoordinateMathBehaviour.getInstance();

    public final int x;
    public final int z;

    public ChunkCoordIntPair(int i, int j) {
        this.x = i;
        this.z = j;
    }

    public static int a(int i, int j) {
        return COORDINATE_MATH_BEHAVIOUR.chunkPairKey(i, j);
    }

    public int hashCode() {
        return COORDINATE_MATH_BEHAVIOUR.chunkPairKey(this.x, this.z);
    }

    public boolean equals(Object object) {
        return COORDINATE_MATH_BEHAVIOUR.equals(this, object);
    }
}
