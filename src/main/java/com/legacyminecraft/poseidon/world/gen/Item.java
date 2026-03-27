package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation item scaffold.
 */
public class Item {
    public static final Item[] byId = new Item[4096];

    public static final Item SADDLE = register(new Item(329));
    public static final Item IRON_INGOT = register(new Item(265));
    public static final Item BREAD = register(new Item(297));
    public static final Item WHEAT = register(new Item(296));
    public static final Item SULPHUR = register(new Item(289));
    public static final Item STRING = register(new Item(287));
    public static final Item BUCKET = register(new Item(325));
    public static final Item GOLDEN_APPLE = register(new Item(322));
    public static final Item REDSTONE = register(new Item(331));
    public static final Item GOLD_RECORD = register(new Item(2256));
    public static final Item INK_SACK = register(new Item(351));

    public final int id;

    public Item(int id) {
        this.id = id;
    }

    private static Item register(Item item) {
        if (item.id >= 0 && item.id < byId.length) {
            byId[item.id] = item;
        }
        return item;
    }
}
