package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat item scaffold.
 */
public class Item {
    public static final Item[] byId = new Item[4096];
    public static final Item MAP = new Item(358, "MAP");
    public static final Item BUCKET = new Item(325, "BUCKET");
    public static final Item ARROW = new Item(262, "ARROW");
    public static final Item BONE = new Item(352, "BONE");
    public static final Item WOOL = new Item(35, "WOOL");
    public static final Item INK_SACK = new Item(351, "INK_SACK");
    public static final Item FEATHER = new Item(288, "FEATHER");
    public static final Item STRING = new Item(287, "STRING");
    public static final Item PAINTING = new Item(321, "PAINTING");

    public final int id;
    private final String name;

    public Item(int id, String name) {
        this.id = id;
        this.name = name;
        if (id >= 0 && id < byId.length) {
            byId[id] = this;
        }
    }

    public int getMaxStackSize() {
        return 64;
    }

    public boolean d() {
        return false;
    }

    public int e() {
        return 0;
    }

    public String j() {
        return name;
    }

    public WorldMap a(ItemStack stack, WorldServer worldServer) {
        return new WorldMap();
    }
}
