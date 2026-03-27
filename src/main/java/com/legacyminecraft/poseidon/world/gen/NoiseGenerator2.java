package com.legacyminecraft.poseidon.world.gen;

import java.util.Random;

/**
 * Simplex noise octave scaffold.
 */
public class NoiseGenerator2 extends NoiseGenerator {
    public NoiseGenerator2(Random random) {
    }

    public void a(
            double[] output,
            double startX,
            double startZ,
            int sizeX,
            int sizeZ,
            double scaleX,
            double scaleZ,
            double amplitude
    ) {
        int sampleCount = sizeX * sizeZ;
        if (output == null) {
            return;
        }
        for (int i = 0; i < sampleCount && i < output.length; i++) {
            output[i] += 0.0D;
        }
    }
}
