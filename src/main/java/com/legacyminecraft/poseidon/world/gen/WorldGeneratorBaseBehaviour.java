package com.legacyminecraft.poseidon.world.gen;


/**
 * Canonical base hook for legacy world-generator wrappers.
 */
public final class WorldGeneratorBaseBehaviour {
    private static final WorldGeneratorBaseBehaviour INSTANCE = new WorldGeneratorBaseBehaviour();

    private WorldGeneratorBaseBehaviour() {
    }

    public static WorldGeneratorBaseBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(WorldGenerator worldGenerator) {
        // Base wrapper currently carries no mutable initialization state.
    }

    public void configureCoordinateScale(WorldGenerator worldGenerator, double xScale, double yScale, double zScale) {
        // Hook retained for compatibility. Vanilla base implementation is a no-op.
    }
}
