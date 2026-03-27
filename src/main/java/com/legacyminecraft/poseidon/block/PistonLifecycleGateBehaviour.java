package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical lifecycle gate behaviour for piston tile ticks/finalization checks.
 */
public final class PistonLifecycleGateBehaviour {
    private static final PistonLifecycleGateBehaviour INSTANCE = new PistonLifecycleGateBehaviour();

    private PistonLifecycleGateBehaviour() {
    }

    public static PistonLifecycleGateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldSkipTick(World world) {
        return world == null;
    }

    public boolean shouldFinalizeOnTick(float previousProgress) {
        return previousProgress >= 1.0F;
    }

    public boolean shouldFinalizeImmediately(float previousProgress) {
        return previousProgress < 1.0F;
    }
}
