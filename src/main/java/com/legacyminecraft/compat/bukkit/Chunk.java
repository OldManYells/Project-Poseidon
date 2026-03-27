package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat chunk scaffold.
 */
public class Chunk {
    public int x;
    public int z;
    public WorldServer worldServer;
    public boolean done;
    public CraftChunk bukkitChunk = new CraftChunk();

    public Chunk() {
    }

    public Chunk(WorldServer worldServer, byte[] blockTypes, int x, int z) {
        this.worldServer = worldServer;
        this.x = x;
        this.z = z;
    }

    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    public int getData(int x, int y, int z) {
        return 0;
    }

    public void getData(byte[] buffer, int minX, int minY, int minZ, int sizeX, int sizeY, int sizeZ, int section) {
    }

    public void setTypeId(int x, int y, int z, int typeId) {
    }

    public void initLighting() {
    }

    public void loadNOP() {
    }

    public void addEntities() {
    }

    public void removeEntities() {
    }

    public boolean isEmpty() {
        return false;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public Block getBlock(int x, int y, int z) {
        return new Block(new World(), x, y, z);
    }
}
