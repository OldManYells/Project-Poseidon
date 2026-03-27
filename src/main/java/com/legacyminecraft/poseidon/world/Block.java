package com.legacyminecraft.poseidon.world;

import java.util.HashSet;
import java.util.Set;

/**
 * Minimal block registry and hooks for the world package.
 */
public class Block {
    public static final Block[] byId = new Block[4096];
    public static final int[] q = new int[4096];
    public static final boolean[] o = new boolean[4096];
    public static final boolean[] t = new boolean[4096];
    public static final int[] s = new int[4096];
    public static final boolean[] isTileEntity = new boolean[4096];
    public static final Set<Integer> leafDecayBlacklist = new HashSet<Integer>();

    public static final Block STONE = register(new Block(1, Material.STONE, "stone"));
    public static final Block GRASS = register(new Block(2, Material.DIRT, "grass"));
    public static final Block DIRT = register(new Block(3, Material.DIRT, "dirt"));
    public static final Block COBBLESTONE = register(new Block(4, Material.STONE, "cobblestone"));
    public static final Block LOG = register(new Block(17, Material.WOOD, "log"));
    public static final Block LEAVES = register(new Block(18, Material.WOOD, "leaves"));
    public static final Block STATIONARY_WATER = register(new Block(9, Material.STATIONARY_WATER, "stationary_water"));
    public static final Block WATER = register(new Block(8, Material.WATER, "water"));
    public static final Block STATIONARY_LAVA = register(new Block(11, Material.STATIONARY_LAVA, "stationary_lava"));
    public static final Block LAVA = register(new Block(10, Material.LAVA, "lava"));
    public static final Block PUMPKIN = register(new Block(86, Material.WOOD, "pumpkin"));
    public static final Block JACK_O_LANTERN = register(new Block(91, Material.WOOD, "jack_o_lantern"));
    public static final Block FURNACE = register(new BlockContainer(61, Material.STONE, "furnace"));
    public static final Block BURNING_FURNACE = register(new BlockContainer(62, Material.STONE, "burning_furnace"));
    public static final Block REDSTONE_ORE = register(new Block(73, Material.STONE, "redstone_ore"));
    public static final Block GLOWING_REDSTONE_ORE = register(new Block(74, Material.STONE, "glowing_redstone_ore"));
    public static final Block DIODE_OFF = register(new Block(93, Material.STONE, "diode_off"));
    public static final Block DIODE_ON = register(new Block(94, Material.STONE, "diode_on"));
    public static final Block REDSTONE_TORCH_OFF = register(new Block(75, Material.WOOD, "redstone_torch_off"));
    public static final Block REDSTONE_TORCH_ON = register(new Block(76, Material.WOOD, "redstone_torch_on"));
    public static final Block RED_MUSHROOM = register(new BlockFlower(40, Material.WOOD, "red_mushroom"));
    public static final Block BROWN_MUSHROOM = register(new BlockFlower(39, Material.WOOD, "brown_mushroom"));
    public static final Block DOUBLE_STEP = register(new Block(43, Material.STONE, "double_step"));
    public static final Block STEP = register(new Block(44, Material.STONE, "step"));
    public static final Block SOIL = register(new Block(60, Material.DIRT, "soil"));
    public static final Block NETHERRACK = register(new Block(87, Material.STONE, "netherrack"));
    public static final Block PORTAL = register(new Block(90, Material.STONE, "portal"));
    public static final Block GLOWSTONE = register(new Block(89, Material.STONE, "glowstone"));
    public static final Block SUGAR_CANE_BLOCK = register(new BlockFlower(83, Material.WOOD, "sugar_cane_block"));
    public static final Block WOOD_STAIRS = register(new Block(53, Material.WOOD, "wood_stairs"));
    public static final Block COBBLESTONE_STAIRS = register(new Block(67, Material.STONE, "cobblestone_stairs"));
    public static final Block CACTUS = register(new BlockFlower(81, Material.SAND, "cactus"));
    public static final Block CHEST = register(new BlockContainer(54, Material.WOOD, "chest"));
    public static final Block MOB_SPAWNER = register(new BlockContainer(52, Material.STONE, "mob_spawner"));
    public static final Block MOSSY_COBBLESTONE = register(new Block(48, Material.STONE, "mossy_cobblestone"));
    public static final Block RAILS = register(new Block(66, Material.STONE, "rails"));
    public static final Block WORKBENCH = register(new BlockContainer(58, Material.WOOD, "workbench"));
    public static final Block DISPENSER = register(new BlockContainer(23, Material.STONE, "dispenser"));
    public static final Block FIRE = register(new Block(51, Material.AIR, "fire"));
    public static final Block SAND = register(new Block(12, Material.SAND, "sand"));
    public static final Block BEDROCK = register(new Block(7, Material.STONE, "bedrock"));

    public final int id;
    public final Material material;
    private final String name;

    public Block(int id, Material material, String name) {
        this.id = id;
        this.material = material;
        this.name = name;
    }

    private static Block register(Block block) {
        if (block.id >= 0 && block.id < byId.length) {
            byId[block.id] = block;
            q[block.id] = block.material.isSolid() ? 3 : 0;
            s[block.id] = block.material.isSolid() ? 15 : 0;
        }
        return block;
    }

    public boolean m() {
        return material.isSolid();
    }

    public boolean b() {
        return material.isSolid();
    }

    public String k() {
        return name;
    }

    public void remove(World world, int x, int y, int z) {
    }

    public void c(World world, int x, int y, int z) {
    }

    public void a(World world, int x, int y, int z, java.util.Random random) {
    }

    public void g(World world, int x, int y, int z, int data) {
    }

    public boolean f(World world, int x, int y, int z) {
        return true;
    }

    public void doPhysics(World world, int x, int y, int z, int typeId) {
    }
}
