package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.Block;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Material;

/**
 * Canonical furnace fuel burn-time policy behaviour.
 */
public final class FurnaceFuelBurnTimeBehaviour {
    private static final FurnaceFuelBurnTimeBehaviour INSTANCE = new FurnaceFuelBurnTimeBehaviour();

    private FurnaceFuelBurnTimeBehaviour() {
    }

    public static FurnaceFuelBurnTimeBehaviour getInstance() {
        return INSTANCE;
    }

    public int getBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        }

        int itemId = stack.getItem().id;

        if (itemId < 256 && Block.byId[itemId].material == Material.WOOD) {
            return 300;
        }

        if (itemId == Item.STICK.id) {
            return 100;
        }

        if (itemId == Item.COAL.id) {
            return 1600;
        }

        if (itemId == Item.LAVA_BUCKET.id) {
            return 20000;
        }

        if (itemId == Block.SAPLING.id) {
            return 100;
        }

        return 0;
    }
}
