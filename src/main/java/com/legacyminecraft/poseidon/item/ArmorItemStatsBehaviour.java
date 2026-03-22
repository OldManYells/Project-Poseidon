package com.legacyminecraft.poseidon.item;

public final class ArmorItemStatsBehaviour {
    private static final ArmorItemStatsBehaviour INSTANCE = new ArmorItemStatsBehaviour();

    private static final int[] ARMOR_POINTS = new int[] { 3, 8, 6, 3 };
    private static final int[] BASE_DURABILITY = new int[] { 11, 16, 15, 13 };

    private ArmorItemStatsBehaviour() {
    }

    public static ArmorItemStatsBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveArmorPoints(int armorSlot) {
        return ARMOR_POINTS[armorSlot];
    }

    public int resolveDurability(int armorSlot, int armorTier) {
        return BASE_DURABILITY[armorSlot] * 3 << armorTier;
    }
}
