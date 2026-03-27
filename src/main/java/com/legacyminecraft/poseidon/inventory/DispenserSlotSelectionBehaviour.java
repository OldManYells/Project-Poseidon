package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Field;
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

    public int findDispenseSlot(Object[] items, Random random) {
        int selectedSlot = -1;
        int candidateWeight = 1;

        for (int slot = 0; slot < items.length; ++slot) {
            Object stack = items[slot];

            if (stack == null || stackCount(stack) == 0) {
                continue;
            }

            if (random.nextInt(candidateWeight++) == 0) {
                selectedSlot = slot;
            }
        }

        return selectedSlot;
    }

    private static int stackCount(Object stack) {
        try {
            Field field = stack.getClass().getDeclaredField("count");
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack count", exception);
        }
    }
}
