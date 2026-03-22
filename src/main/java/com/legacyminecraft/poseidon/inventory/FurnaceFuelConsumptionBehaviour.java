package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical behaviour for consuming furnace fuel-slot items.
 */
public final class FurnaceFuelConsumptionBehaviour {
    private static final FurnaceFuelConsumptionBehaviour INSTANCE = new FurnaceFuelConsumptionBehaviour();

    private FurnaceFuelConsumptionBehaviour() {
    }

    public static FurnaceFuelConsumptionBehaviour getInstance() {
        return INSTANCE;
    }

    public void consumeFuel(ItemStack[] items, int fuelSlot) {
        if (items[fuelSlot] == null) {
            return;
        }

        --items[fuelSlot].count;
        if (items[fuelSlot].count == 0) {
            items[fuelSlot] = null;
        }
    }
}
