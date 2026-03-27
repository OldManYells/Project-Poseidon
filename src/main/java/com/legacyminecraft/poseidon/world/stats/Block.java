package com.legacyminecraft.poseidon.world.stats;

import java.util.HashSet;
import java.util.Set;

/**
 * Minimal block registry for stat bootstrap logic.
 */
public class Block {
    public static final Block[] byId = new Block[4096];
    public static final boolean[] o = new boolean[4096];
    public static final int[] q = new int[4096];

    public static final Block STATIONARY_WATER = register(new Block(9, "stationary_water"));
    public static final Block WATER = register(new Block(8, "water"));
    public static final Block STATIONARY_LAVA = register(new Block(11, "stationary_lava"));
    public static final Block LAVA = register(new Block(10, "lava"));
    public static final Block JACK_O_LANTERN = register(new Block(91, "jack_o_lantern"));
    public static final Block PUMPKIN = register(new Block(86, "pumpkin"));
    public static final Block BURNING_FURNACE = register(new Block(62, "burning_furnace"));
    public static final Block FURNACE = register(new Block(61, "furnace"));
    public static final Block GLOWING_REDSTONE_ORE = register(new Block(74, "glowing_redstone_ore"));
    public static final Block REDSTONE_ORE = register(new Block(73, "redstone_ore"));
    public static final Block DIODE_ON = register(new Block(94, "diode_on"));
    public static final Block DIODE_OFF = register(new Block(93, "diode_off"));
    public static final Block REDSTONE_TORCH_ON = register(new Block(76, "redstone_torch_on"));
    public static final Block REDSTONE_TORCH_OFF = register(new Block(75, "redstone_torch_off"));
    public static final Block RED_MUSHROOM = register(new Block(40, "red_mushroom"));
    public static final Block BROWN_MUSHROOM = register(new Block(39, "brown_mushroom"));
    public static final Block DOUBLE_STEP = register(new Block(43, "double_step"));
    public static final Block STEP = register(new Block(44, "step"));
    public static final Block GRASS = register(new Block(2, "grass"));
    public static final Block DIRT = register(new Block(3, "dirt"));
    public static final Block SOIL = register(new Block(60, "soil"));
    public static final Block LOG = register(new Block(17, "log"));
    public static final Block WORKBENCH = register(new Block(58, "workbench"));
    public static final Block RAILS = register(new Block(66, "rails"));

    public final int id;
    public final String name;
    private final Set<Integer> equivalents = new HashSet<Integer>();

    public Block(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static Block register(Block block) {
        if (block.id >= 0 && block.id < byId.length) {
            byId[block.id] = block;
            q[block.id] = 1;
        }
        return block;
    }

    public boolean m() {
        return true;
    }

    public String k() {
        return name;
    }

    public Set<Integer> getEquivalents() {
        return equivalents;
    }
}
