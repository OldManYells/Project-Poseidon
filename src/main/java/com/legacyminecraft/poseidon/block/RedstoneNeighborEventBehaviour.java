package com.legacyminecraft.poseidon.block;

/**
 * Canonical neighbor power-source gating policy for legacy redstone event wrappers.
 */
public final class RedstoneNeighborEventBehaviour {
    private static final RedstoneNeighborEventBehaviour INSTANCE = new RedstoneNeighborEventBehaviour();

    private RedstoneNeighborEventBehaviour() {
    }

    public static RedstoneNeighborEventBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldFireNeighborPowerEvent(boolean neighborExists, boolean neighborIsPowerSource) {
        return neighborExists && neighborIsPowerSource;
    }
}
