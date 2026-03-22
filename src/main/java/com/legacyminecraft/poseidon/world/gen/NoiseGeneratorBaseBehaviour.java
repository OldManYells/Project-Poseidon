package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.NoiseGenerator;

/**
 * Canonical base hook for legacy noise-generator wrappers.
 */
public final class NoiseGeneratorBaseBehaviour {
    private static final NoiseGeneratorBaseBehaviour INSTANCE = new NoiseGeneratorBaseBehaviour();

    private NoiseGeneratorBaseBehaviour() {
    }

    public static NoiseGeneratorBaseBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(NoiseGenerator noiseGenerator) {
        // Base wrapper currently carries no mutable initialization state.
    }
}
