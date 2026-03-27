package com.legacyminecraft.poseidon.item;

/**
 * Item-local math helper scaffold.
 */
public final class MathHelper {
    private MathHelper() {
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < (double) i ? i - 1 : i;
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }
}
