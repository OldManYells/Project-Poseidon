package com.legacyminecraft.poseidon.world;

import net.minecraft.server.ChunkCoordIntPair;
import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.MathHelper;
import net.minecraft.server.PathPoint;

public final class CoordinateMathBehaviour {
    private static final CoordinateMathBehaviour INSTANCE = new CoordinateMathBehaviour();

    private CoordinateMathBehaviour() {
    }

    public static CoordinateMathBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean equals(ChunkCoordinates left, Object object) {
        if (!(object instanceof ChunkCoordinates)) {
            return false;
        }

        ChunkCoordinates right = (ChunkCoordinates) object;
        return left.x == right.x && left.y == right.y && left.z == right.z;
    }

    public int hash(ChunkCoordinates coordinates) {
        return coordinates.x + coordinates.z << 8 + coordinates.y << 16;
    }

    public int compare(ChunkCoordinates left, ChunkCoordinates right) {
        return left.y == right.y
                ? (left.z == right.z ? left.x - right.x : left.z - right.z)
                : left.y - right.y;
    }

    public double distance(ChunkCoordinates coordinates, int i, int j, int k) {
        int l = coordinates.x - i;
        int i1 = coordinates.y - j;
        int j1 = coordinates.z - k;

        return Math.sqrt((double) (l * l + i1 * i1 + j1 * j1));
    }

    public int pathPointKey(int i, int j, int k) {
        return j & 255 | (i & 32767) << 8 | (k & 32767) << 24 | (i < 0 ? Integer.MIN_VALUE : 0) | (k < 0 ? '\u8000' : 0);
    }

    public float distance(PathPoint from, PathPoint to) {
        float f = (float) (to.a - from.a);
        float f1 = (float) (to.b - from.b);
        float f2 = (float) (to.c - from.c);

        return MathHelper.c(f * f + f1 * f1 + f2 * f2);
    }

    public boolean equals(PathPoint left, Object object) {
        if (!(object instanceof PathPoint)) {
            return false;
        }

        PathPoint right = (PathPoint) object;
        return left.hashCode() == right.hashCode() && left.a == right.a && left.b == right.b && left.c == right.c;
    }

    public boolean isAssigned(PathPoint pathPoint, int index) {
        return index >= 0;
    }

    public String stringify(PathPoint pathPoint) {
        return pathPoint.a + ", " + pathPoint.b + ", " + pathPoint.c;
    }

    public int chunkPairKey(int i, int j) {
        return (i < 0 ? Integer.MIN_VALUE : 0) | (i & 32767) << 16 | (j < 0 ? '\u8000' : 0) | j & 32767;
    }

    public boolean equals(ChunkCoordIntPair left, Object object) {
        ChunkCoordIntPair right = (ChunkCoordIntPair) object;
        return right.x == left.x && right.z == left.z;
    }
}
