package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local item registry scaffold.
 */
public class Item {
    public static final Item[] byId = new Item[512];

    public static final Item BUCKET = new Item(325);
    public static final Item MILK_BUCKET = new Item(335);
    public static final Item LEATHER = new Item(334);
    public static final Item BONE = new Item(352);
    public static final Item PORK = new ItemFood(319, true, 3);
    public static final Item GRILLED_PORK = new ItemFood(320, true, 8);
    public static final Item FEATHER = new Item(288);
    public static final Item ARROW = new Item(262);
    public static final Item STRING = new Item(287);
    public static final Item STICK = new Item(280);
    public static final Item MINECART = new Item(328);
    public static final Item COAL = new Item(263);
    public static final Item SHEARS = new Item(359);
    public static final Item SLIME_BALL = new Item(341);
    public static final Item SULPHUR = new Item(289);
    public static final Item GOLD_RECORD = new Item(2256);
    public static final Item PAINTING = new Item(321);

    public final int id;

    public Item(int id) {
        this.id = id;
        if (id >= 0 && id < byId.length) {
            byId[id] = this;
        }
    }
}
