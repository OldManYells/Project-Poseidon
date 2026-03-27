package com.legacyminecraft.poseidon.world.gen;

import java.util.Random;

/**
 * Octaved noise sampler scaffold.
 */
public class NoiseGeneratorOctaves2 extends NoiseGenerator {
    private final NoiseGenerator2[] octaves;

    public NoiseGeneratorOctaves2(Random random, int octaveCount) {
        this.octaves = OctavedSimplexNoiseBehaviour.getInstance().createOctaves(random, octaveCount);
    }

    public double[] a(
            double[] output,
            double startX,
            double startZ,
            int sizeX,
            int sizeZ,
            double scaleX,
            double scaleZ,
            double persistence
    ) {
        return OctavedSimplexNoiseBehaviour.getInstance().sampleOctaved(
                output,
                startX,
                startZ,
                sizeX,
                sizeZ,
                scaleX,
                scaleZ,
                2.0D,
                persistence,
                octaves,
                octaves.length
        );
    }
}
