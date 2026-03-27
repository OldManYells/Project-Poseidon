package com.legacyminecraft.poseidon.inventory;


import com.legacyminecraft.poseidon.item.Block;

import java.lang.reflect.Field;

/**
 * Canonical furnace fuel burn-time policy behaviour.
 */
public final class FurnaceFuelBurnTimeBehaviour {
    private static final FurnaceFuelBurnTimeBehaviour INSTANCE = new FurnaceFuelBurnTimeBehaviour();
    private static final int STICK_ITEM_ID = 280;
    private static final int COAL_ITEM_ID = 263;

    private FurnaceFuelBurnTimeBehaviour() {
    }

    public static FurnaceFuelBurnTimeBehaviour getInstance() {
        return INSTANCE;
    }

    public int getBurnTime(Object stack) {
        if (stack == null) {
            return 0;
        }

        int itemId = itemId(stack);

        if (itemId < 256 && Block.byId[itemId] != null && itemId == Block.WOOD.id) {
            return 300;
        }

        if (itemId == STICK_ITEM_ID) {
            return 100;
        }

        if (itemId == COAL_ITEM_ID) {
            return 1600;
        }

        if (itemId == 327) {
            return 20000;
        }

        if (itemId == Block.SAPLING.id) {
            return 100;
        }

        return 0;
    }

    private static int itemId(Object stack) {
        try {
            Object item = stack.getClass().getMethod("getItem").invoke(stack);
            if (item == null) {
                return -1;
            }
            Field field = item.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return ((Number) field.get(item)).intValue();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read item id", exception);
        }
    }
}
