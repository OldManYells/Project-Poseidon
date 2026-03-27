package com.legacyminecraft.poseidon.world;

/**
 * Minimal light-update bounds holder used by metadata update behavior.
 */
public class MetadataChunkBlock {
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;

    public MetadataChunkBlock() {
    }

    public MetadataChunkBlock(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        this.b = minX;
        this.c = minY;
        this.d = minZ;
        this.e = maxX;
        this.f = maxY;
        this.g = maxZ;
    }
}
