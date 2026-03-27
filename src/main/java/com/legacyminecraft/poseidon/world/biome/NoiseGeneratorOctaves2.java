package com.legacyminecraft.poseidon.world.biome;

import java.util.Random;

/**
 * Biome-local alias for octaved noise sampling.
 */
public class NoiseGeneratorOctaves2 extends com.legacyminecraft.poseidon.world.gen.NoiseGeneratorOctaves2 {
    public NoiseGeneratorOctaves2(Random random, int octaveCount) {
        super(random, octaveCount);
    }
}
