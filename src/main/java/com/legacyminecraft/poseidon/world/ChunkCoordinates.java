package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.world.CoordinateMathBehaviour;

public class ChunkCoordinates implements Comparable {
    private static final CoordinateMathBehaviour COORDINATE_MATH_BEHAVIOUR = CoordinateMathBehaviour.getInstance();

    public int x;
    public int y;
    public int z;

    public ChunkCoordinates() {}

    public ChunkCoordinates(int i, int j, int k) {
        this.x = i;
        this.y = j;
        this.z = k;
    }

    public ChunkCoordinates(ChunkCoordinates chunkcoordinates) {
        this.x = chunkcoordinates.x;
        this.y = chunkcoordinates.y;
        this.z = chunkcoordinates.z;
    }

    public boolean equals(Object object) {
        return COORDINATE_MATH_BEHAVIOUR.equals(this, object);
    }

    public int hashCode() {
        return COORDINATE_MATH_BEHAVIOUR.hash(this);
    }

    public int compareTo(Object o) {
        ChunkCoordinates chunkcoordinates = (ChunkCoordinates) o;
        return COORDINATE_MATH_BEHAVIOUR.compare(this, chunkcoordinates);
    }

    public double a(int i, int j, int k) {
        return COORDINATE_MATH_BEHAVIOUR.distance(this, i, j, k);
    }
}
