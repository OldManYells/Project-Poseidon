package com.legacyminecraft.poseidon.block;

/**
 * Canonical interpolation behaviour for piston tile-entity animation progress.
 */
public final class PistonProgressInterpolationBehaviour {
    private static final PistonProgressInterpolationBehaviour INSTANCE = new PistonProgressInterpolationBehaviour();

    private PistonProgressInterpolationBehaviour() {
    }

    public static PistonProgressInterpolationBehaviour getInstance() {
        return INSTANCE;
    }

    public float interpolate(float previousProgress, float currentProgress, float partialTick) {
        float clampedPartialTick = partialTick;
        if (clampedPartialTick > 1.0F) {
            clampedPartialTick = 1.0F;
        }

        return previousProgress + (currentProgress - previousProgress) * clampedPartialTick;
    }
}
