package com.legacyminecraft.compat.bukkit;

/**
 * Canonical math helper used by packet and movement codecs.
 */
public final class MathHelper {
    private MathHelper() {
    }

    public static int floor(double value) {
        int floor = (int) value;
        return value < (double) floor ? floor - 1 : floor;
    }

    public static float a(double value) {
        return (float) Math.sqrt(value);
    }

    public static float a(float value) {
        return (float) Math.sqrt((double) value);
    }

    public static double a(double x, double z) {
        return Math.sqrt(x * x + z * z);
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }
}
