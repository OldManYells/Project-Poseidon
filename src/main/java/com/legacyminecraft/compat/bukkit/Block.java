package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat block scaffold.
 */
public class Block {
    public static final Block REDSTONE_WIRE;
    private World world = new World();
    private int x;
    private int y;
    private int z;
    private int typeId;
    private byte data;
    private byte lightLevel;
    private CraftChunk chunk = new CraftChunk();

    static {
        REDSTONE_WIRE = new Block();
        REDSTONE_WIRE.setTypeId(Material.REDSTONE_WIRE.getId());
    }

    public Block() {
    }

    public Block(World world, int x, int y, int z) {
        this.world = world == null ? new World() : world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public void setCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Material getType() {
        return Material.getMaterial(typeId);
    }

    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }

    public byte getLightLevel() {
        return lightLevel;
    }

    public CraftChunk getChunk() {
        return chunk;
    }

    public void setChunk(CraftChunk chunk) {
        this.chunk = chunk == null ? new CraftChunk() : chunk;
    }

    public BlockState getState() {
        return new BlockState(this);
    }
}
