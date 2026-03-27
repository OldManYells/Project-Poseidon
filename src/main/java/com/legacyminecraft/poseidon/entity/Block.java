package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local block registry scaffold.
 */
public class Block {
    public static final Block[] byId = new Block[4096];
    public static final boolean[] o = new boolean[4096];

    public static final Block FIRE = new Block(51);
    public static final Block GRASS = new Block(2);
    public static final Block LOG = new Block(17);
    public static final Block STONE = new Block(1);
    public static final Block WOOD = new Block(5);
    public static final Block SAND = new Block(12);
    public static final Block GRAVEL = new Block(13);
    public static final Block WOOL = new Block(35);
    public static final Block CHEST = new Block(54);
    public static final Block FURNACE = new Block(61);

    public final int id;
    public Material material = Material.SOLID;

    public Block(int id) {
        this.id = id;
        if (id >= 0 && id < o.length) {
            o[id] = true;
        }
        if (id >= 0 && id < byId.length) {
            byId[id] = this;
        }
    }

    public boolean canPlace(World world, int x, int y, int z) {
        return true;
    }
}
