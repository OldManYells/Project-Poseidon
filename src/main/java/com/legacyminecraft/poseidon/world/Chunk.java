package com.legacyminecraft.poseidon.world;

/**
 * Canonical chunk scaffold used by migrated map/snapshot logic.
 */
public class Chunk {
    public final World world;
    public final int x;
    public final int z;
    public final byte[] heightMap = new byte[256];

    public Chunk(World world) {
        this(world, 0, 0);
    }

    public Chunk(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public int getData(byte[] destination, int x, int y, int z, int width, int height, int depth, int offset) {
        if (destination != null) {
            for (int i = 0; i < destination.length; i++) {
                destination[i] = 0;
            }
        }
        return offset;
    }

    public boolean isEmpty() {
        return false;
    }

    public int b(int x, int z) {
        return 0;
    }

    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    public boolean a(int chunkX, int chunkZ) {
        return true;
    }

    public boolean a(int x, int y, int z, int typeId, int data) {
        return true;
    }

    public boolean a(int x, int y, int z, int typeId) {
        return true;
    }

    public void b(int x, int y, int z, int data) {
    }

    public boolean c(int x, int y, int z) {
        return false;
    }

    public int c(int x, int y, int z, int fallback) {
        return 0;
    }

    public int getData(int x, int y, int z) {
        return 0;
    }

    public void a(Entity entity) {
    }

    public void b(Entity entity) {
    }
}
