package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.NoiseGenerator2;

import java.util.Random;

/**
 * Canonical octave composition behaviour for legacy simplex noise wrappers.
 */
public final class OctavedSimplexNoiseBehaviour {
    private static final OctavedSimplexNoiseBehaviour INSTANCE = new OctavedSimplexNoiseBehaviour();

    private OctavedSimplexNoiseBehaviour() {
    }

    public static OctavedSimplexNoiseBehaviour getInstance() {
        return INSTANCE;
    }

    public NoiseGenerator2[] createOctaves(Random random, int octaveCount) {
        NoiseGenerator2[] octaveSamplers = new NoiseGenerator2[octaveCount];

        for (int octaveIndex = 0; octaveIndex < octaveCount; ++octaveIndex) {
            octaveSamplers[octaveIndex] = new NoiseGenerator2(random);
        }

        return octaveSamplers;
    }

    public double[] sampleOctaved(double[] outputNoise,
                                  double startX,
                                  double startZ,
                                  int sizeX,
                                  int sizeZ,
                                  double scaleX,
                                  double scaleZ,
                                  double lacunarity,
                                  double persistence,
                                  NoiseGenerator2[] octaveSamplers,
                                  int octaveCount) {
        scaleX /= 1.5D;
        scaleZ /= 1.5D;
        if (outputNoise != null && outputNoise.length >= sizeX * sizeZ) {
            for (int sampleIndex = 0; sampleIndex < outputNoise.length; ++sampleIndex) {
                outputNoise[sampleIndex] = 0.0D;
            }
        } else {
            outputNoise = new double[sizeX * sizeZ];
        }

        double persistenceScale = 1.0D;
        double frequencyScale = 1.0D;

        for (int octaveIndex = 0; octaveIndex < octaveCount; ++octaveIndex) {
            octaveSamplers[octaveIndex].a(outputNoise, startX, startZ, sizeX, sizeZ,
                    scaleX * frequencyScale, scaleZ * frequencyScale, 0.55D / persistenceScale);
            frequencyScale *= lacunarity;
            persistenceScale *= persistence;
        }

        return outputNoise;
    }
}
