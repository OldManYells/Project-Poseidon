package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.NoiseGeneratorBaseBehaviour;

public abstract class NoiseGenerator {
    private static final NoiseGeneratorBaseBehaviour NOISE_GENERATOR_BASE_BEHAVIOUR = NoiseGeneratorBaseBehaviour.getInstance();

    public NoiseGenerator() {
        NOISE_GENERATOR_BASE_BEHAVIOUR.initialize(this);
    }
}
