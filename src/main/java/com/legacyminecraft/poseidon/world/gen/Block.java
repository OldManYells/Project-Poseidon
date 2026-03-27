package com.legacyminecraft.poseidon.world.gen;

import java.util.HashSet;
import java.util.Set;

/**
 * World-generation block scaffold.
 */
public class Block {
    public static final Block[] byId = new Block[4096];
    public static final boolean[] o = new boolean[4096];
    public static final Set<Integer> leafDecayBlacklist = new HashSet<Integer>();

    public static final Block STONE = register(new Block(1, Material.SOLID));
    public static final Block DIRT = register(new Block(3, Material.SOLID));
    public static final Block GRASS = register(new Block(2, Material.SOLID));
    public static final Block LEAVES = register(new Block(18, Material.SOLID));
    public static final Block LOG = register(new Block(17, Material.SOLID));
    public static final Block SAND = register(new Block(12, Material.SOLID));
    public static final Block GRAVEL = register(new Block(13, Material.SOLID));
    public static final Block MOSSY_COBBLESTONE = register(new Block(48, Material.SOLID));
    public static final Block COBBLESTONE = register(new Block(4, Material.SOLID));
    public static final Block CHEST = register(new Block(54, Material.SOLID));
    public static final Block MOB_SPAWNER = register(new Block(52, Material.SOLID));
    public static final Block PUMPKIN = register(new Block(86, Material.SOLID));
    public static final Block YELLOW_FLOWER = register(new BlockFlower(37, Material.SOLID));
    public static final Block RED_ROSE = register(new BlockFlower(38, Material.SOLID));
    public static final Block LONG_GRASS = register(new BlockFlower(31, Material.SOLID));

    public final int id;
    public final Material material;

    public Block(int id, Material material) {
        this.id = id;
        this.material = material;
    }

    private static Block register(Block block) {
        if (block.id >= 0 && block.id < byId.length) {
            byId[block.id] = block;
        }
        return block;
    }

    public boolean canPlace(World world, int x, int y, int z) {
        return true;
    }
}
