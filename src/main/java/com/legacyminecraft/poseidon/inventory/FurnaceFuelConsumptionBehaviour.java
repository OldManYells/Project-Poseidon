package com.legacyminecraft.poseidon.inventory;


import java.lang.reflect.Field;

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

    public void consumeFuel(Object[] items, int fuelSlot) {
        if (items[fuelSlot] == null) {
            return;
        }

        setCount(items[fuelSlot], count(items[fuelSlot]) - 1);
        if (count(items[fuelSlot]) == 0) {
            items[fuelSlot] = null;
        }
    }

    private static int count(Object stack) {
        try {
            Field field = stack.getClass().getDeclaredField("count");
            field.setAccessible(true);
            return ((Number) field.get(stack)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read stack count", exception);
        }
    }

    private static void setCount(Object stack, int value) {
        try {
            Field field = stack.getClass().getDeclaredField("count");
            field.setAccessible(true);
            field.set(stack, Integer.valueOf(value));
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to write stack count", exception);
        }
    }
}
