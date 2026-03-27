package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local math helper alias.
 */
public final class MathHelper {
    private MathHelper() {
    }

    public static int floor(double value) {
        int floor = (int) value;
        return value < (double) floor ? floor - 1 : floor;
    }

    public static int d(float value) {
        return (int) Math.floor(value);
    }

    public static float a(double value) {
        return (float) Math.sqrt(value);
    }

    public static double a(double x, double z) {
        return Math.sqrt(x * x + z * z);
    }

    public static float c(float value) {
        return (float) Math.sqrt((double) value);
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }
}
