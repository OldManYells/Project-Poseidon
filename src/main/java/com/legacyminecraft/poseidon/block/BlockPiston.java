package com.legacyminecraft.poseidon.block;

/**
 * Canonical piston state helpers scaffold.
 */
public final class BlockPiston {
    private BlockPiston() {
    }

    public static boolean d(int data) {
        return (data & 8) == 8;
    }
}
