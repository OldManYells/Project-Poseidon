package com.legacyminecraft.poseidon.world.gen;

public final class MathHelper {
    private MathHelper() {
    }

    public static float sin(float value) {
        return (float) Math.sin(value);
    }

    public static float cos(float value) {
        return (float) Math.cos(value);
    }

    public static int floor(double value) {
        return com.legacyminecraft.poseidon.world.MathHelper.floor(value);
    }
}
