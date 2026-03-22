package com.legacyminecraft.poseidon.block;

/**
 * Canonical piston per-tick progress stepping behaviour.
 */
public final class PistonTickProgressionBehaviour {
    private static final PistonTickProgressionBehaviour INSTANCE = new PistonTickProgressionBehaviour();

    private PistonTickProgressionBehaviour() {
    }

    public static PistonTickProgressionBehaviour getInstance() {
        return INSTANCE;
    }

    public float advanceProgress(float currentProgress, float increment) {
        float nextProgress = currentProgress + increment;
        if (nextProgress >= 1.0F) {
            return 1.0F;
        }

        return nextProgress;
    }

    public float computePushDelta(float currentProgress, float previousProgress, float baseOffset) {
        return currentProgress - previousProgress + baseOffset;
    }

    public boolean shouldPushEntities(boolean isExtending) {
        return isExtending;
    }
}
