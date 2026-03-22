package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

import java.util.Random;

/**
 * Canonical weighted random dispenser slot selection behaviour.
 */
public final class DispenserSlotSelectionBehaviour {
    private static final DispenserSlotSelectionBehaviour INSTANCE = new DispenserSlotSelectionBehaviour();

    private DispenserSlotSelectionBehaviour() {
    }

    public static DispenserSlotSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public int findDispenseSlot(ItemStack[] items, Random random) {
        int selectedSlot = -1;
        int candidateWeight = 1;

        for (int slot = 0; slot < items.length; ++slot) {
            ItemStack stack = items[slot];

            if (stack == null || stack.count == 0) {
                continue;
            }

            if (random.nextInt(candidateWeight++) == 0) {
                selectedSlot = slot;
            }
        }

        return selectedSlot;
    }
}
