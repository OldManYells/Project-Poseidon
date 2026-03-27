package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal item registry for stat bootstrap logic.
 */
public class Item {
    public static final Item[] byId = new Item[32000];

    public static final Item BOOK = register(new Item(340, "book"));
    public static final Item WOOD_PICKAXE = register(new Item(270, "wood_pickaxe", 59));
    public static final Item IRON_INGOT = register(new Item(265, "iron_ingot"));
    public static final Item WOOD_HOE = register(new Item(290, "wood_hoe", 59));
    public static final Item BREAD = register(new Item(297, "bread"));
    public static final Item CAKE = register(new Item(354, "cake"));
    public static final Item STONE_PICKAXE = register(new Item(274, "stone_pickaxe", 131));
    public static final Item COOKED_FISH = register(new Item(350, "cooked_fish"));
    public static final Item WOOD_SWORD = register(new Item(268, "wood_sword", 59));
    public static final Item BONE = register(new Item(352, "bone"));
    public static final Item LEATHER = register(new Item(334, "leather"));
    public static final Item SADDLE = register(new Item(329, "saddle"));
    public static final Item WHEAT = register(new Item(296, "wheat"));
    public static final Item SULPHUR = register(new Item(289, "sulphur"));
    public static final Item STRING = register(new Item(287, "string"));
    public static final Item BUCKET = register(new Item(325, "bucket"));
    public static final Item GOLDEN_APPLE = register(new Item(322, "golden_apple"));
    public static final Item REDSTONE = register(new Item(331, "redstone"));
    public static final Item GOLD_RECORD = register(new Item(2256, "gold_record"));
    public static final Item INK_SACK = register(new Item(351, "ink_sack"));

    public final int id;
    private final String name;
    private final int maxDurability;

    public Item(int id, String name) {
        this(id, name, 0);
    }

    public Item(int id, String name, int maxDurability) {
        this.id = id;
        this.name = name;
        this.maxDurability = maxDurability;
        if (id >= 0 && id < byId.length) {
            byId[id] = this;
        }
    }

    private static Item register(Item item) {
        return item;
    }

    public String j() {
        return name;
    }

    public boolean f() {
        return maxDurability > 0;
    }
}
