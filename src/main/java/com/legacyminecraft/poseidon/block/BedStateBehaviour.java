package com.legacyminecraft.poseidon.block;

/**
 * Canonical orientation, occupancy, texture, and spawn-position policy for legacy bed wrappers.
 */
public final class BedStateBehaviour {
    private static final BedStateBehaviour INSTANCE = new BedStateBehaviour();

    private BedStateBehaviour() {
    }

    public static BedStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int orientation(int data) {
        return data & 3;
    }

    public boolean isHead(int data) {
        return (data & 8) != 0;
    }

    public boolean isOccupied(int data) {
        return (data & 4) != 0;
    }

    public int setOccupied(int data, boolean occupied) {
        if (occupied) {
            return data | 4;
        }
        return data & -5;
    }

    public int resolveDropItemId(int data, int bedItemId) {
        return isHead(data) ? 0 : bedItemId;
    }

    public int resolveTextureBySide(int side, int data, int textureId, int woodTextureId, int[][] sideLookup) {
        if (side == 0) {
            return woodTextureId;
        }

        int orientation = orientation(data);
        int mappedSide = sideLookup[orientation][side];
        if (isHead(data)) {
            return mappedSide == 2
                    ? textureId + 18
                    : (mappedSide != 5 && mappedSide != 4 ? textureId + 1 : textureId + 17);
        }
        return mappedSide == 3
                ? textureId + 15
                : (mappedSide != 5 && mappedSide != 4 ? textureId : textureId + 16);
    }

    public int resolveHeadXFromPart(int x, int orientation, int[][] offsets, boolean isHeadPart) {
        return isHeadPart ? x : x + offsets[orientation][0];
    }

    public int resolveHeadZFromPart(int z, int orientation, int[][] offsets, boolean isHeadPart) {
        return isHeadPart ? z : z + offsets[orientation][1];
    }

    public int resolveOtherHalfXFromHead(int x, int orientation, int[][] offsets) {
        return x + offsets[orientation][0];
    }

    public int resolveOtherHalfZFromHead(int z, int orientation, int[][] offsets) {
        return z + offsets[orientation][1];
    }

    public int resolveCounterpartXForPhysics(int x, int orientation, int[][] offsets, boolean isHeadPart) {
        return isHeadPart ? x - offsets[orientation][0] : x + offsets[orientation][0];
    }

    public int resolveCounterpartZForPhysics(int z, int orientation, int[][] offsets, boolean isHeadPart) {
        return isHeadPart ? z - offsets[orientation][1] : z + offsets[orientation][1];
    }

    public boolean shouldExplodeInDimension(boolean supportsBeds) {
        return !supportsBeds;
    }

    public boolean shouldRemovePartForMissingCounterpart(int counterpartTypeId, int bedBlockId) {
        return counterpartTypeId != bedBlockId;
    }

    public boolean shouldDropNaturally(int data) {
        return !isHead(data);
    }

    public double centeredCoordinate(int coordinate) {
        return (double) coordinate + 0.5D;
    }

    public double average(double left, double right) {
        return (left + right) / 2.0D;
    }

    public ChunkCoord findSpawnPosition(SpawnQuery query, int x, int y, int z, int orientation, int skip, int[][] offsets) {
        int remaining = skip;
        for (int section = 0; section <= 1; ++section) {
            int startX = x - offsets[orientation][0] * section - 1;
            int startZ = z - offsets[orientation][1] * section - 1;
            int endX = startX + 2;
            int endZ = startZ + 2;

            for (int scanX = startX; scanX <= endX; ++scanX) {
                for (int scanZ = startZ; scanZ <= endZ; ++scanZ) {
                    if (query.isSolidTopSurface(scanX, y - 1, scanZ)
                            && query.isEmpty(scanX, y, scanZ)
                            && query.isEmpty(scanX, y + 1, scanZ)) {
                        if (remaining <= 0) {
                            return new ChunkCoord(scanX, y, scanZ);
                        }
                        --remaining;
                    }
                }
            }
        }

        return null;
    }

    public interface SpawnQuery {
        boolean isSolidTopSurface(int x, int y, int z);

        boolean isEmpty(int x, int y, int z);
    }

    public static final class ChunkCoord {
        public final int x;
        public final int y;
        public final int z;

        public ChunkCoord(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
