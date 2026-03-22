package com.legacyminecraft.poseidon.block;

/**
 * Canonical state behaviour for legacy redstone update records.
 */
public final class RedstoneUpdateStateBehaviour {
    private static final RedstoneUpdateStateBehaviour INSTANCE = new RedstoneUpdateStateBehaviour();

    private RedstoneUpdateStateBehaviour() {
    }

    public static RedstoneUpdateStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveX(int x) {
        return x;
    }

    public int resolveY(int y) {
        return y;
    }

    public int resolveZ(int z) {
        return z;
    }

    public long resolveScheduledTick(long scheduledTick) {
        return scheduledTick;
    }
}
