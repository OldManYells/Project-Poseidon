package com.legacyminecraft.poseidon.world;

/**
 * Canonical math helper scaffold.
 */
public final class MathHelper {
    private MathHelper() {
    }

    public static int floor(double value) {
        int i = (int) value;
        return value < i ? i - 1 : i;
    }

    public static float a(double value) {
        return (float) Math.sqrt(value);
    }

    public static float c(float value) {
        return (float) Math.sqrt(value);
    }
}
