package com.legacyminecraft.poseidon.block;

public final class MathHelper {
    private MathHelper() {
    }

    public static int floor(double value) {
        return com.legacyminecraft.poseidon.world.MathHelper.floor(value);
    }

    public static float abs(float value) {
        return Math.abs(value);
    }
}
