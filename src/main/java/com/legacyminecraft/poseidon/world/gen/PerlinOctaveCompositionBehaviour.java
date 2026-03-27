package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

/**
 * Canonical octave-composition behaviour for legacy Perlin wrappers.
 */
public final class PerlinOctaveCompositionBehaviour {
    private static final PerlinOctaveCompositionBehaviour INSTANCE = new PerlinOctaveCompositionBehaviour();

    private PerlinOctaveCompositionBehaviour() {
    }

    public static PerlinOctaveCompositionBehaviour getInstance() {
        return INSTANCE;
    }

    public NoiseGeneratorPerlin[] createOctaves(Random random, int octaveCount) {
        NoiseGeneratorPerlin[] octaveSamplers = new NoiseGeneratorPerlin[octaveCount];

        for (int octaveIndex = 0; octaveIndex < octaveCount; ++octaveIndex) {
            octaveSamplers[octaveIndex] = new NoiseGeneratorPerlin(random);
        }

        return octaveSamplers;
    }

    public double sample2d(double x, double z, NoiseGeneratorPerlin[] octaveSamplers, int octaveCount) {
        double combinedValue = 0.0D;
        double amplitude = 1.0D;

        for (int octaveIndex = 0; octaveIndex < octaveCount; ++octaveIndex) {
            combinedValue += octaveSamplers[octaveIndex].a(x * amplitude, z * amplitude) / amplitude;
            amplitude /= 2.0D;
        }

        return combinedValue;
    }

    public double[] sample3d(double[] outputNoise,
                             double startX,
                             double startY,
                             double startZ,
                             int sizeX,
                             int sizeY,
                             int sizeZ,
                             double scaleX,
                             double scaleY,
                             double scaleZ,
                             NoiseGeneratorPerlin[] octaveSamplers,
                             int octaveCount) {
        if (outputNoise == null) {
            outputNoise = new double[sizeX * sizeY * sizeZ];
        } else {
            for (int sampleIndex = 0; sampleIndex < outputNoise.length; ++sampleIndex) {
                outputNoise[sampleIndex] = 0.0D;
            }
        }

        double amplitude = 1.0D;

        for (int octaveIndex = 0; octaveIndex < octaveCount; ++octaveIndex) {
            octaveSamplers[octaveIndex].a(outputNoise, startX, startY, startZ, sizeX, sizeY, sizeZ,
                    scaleX * amplitude, scaleY * amplitude, scaleZ * amplitude, amplitude);
            amplitude /= 2.0D;
        }

        return outputNoise;
    }

    public double[] sample2dSlice(double[] outputNoise,
                                  int startX,
                                  int startZ,
                                  int sizeX,
                                  int sizeZ,
                                  double scaleX,
                                  double scaleZ,
                                  NoiseGeneratorPerlin[] octaveSamplers,
                                  int octaveCount) {
        return sample3d(outputNoise, (double) startX, 10.0D, (double) startZ, sizeX, 1, sizeZ,
                scaleX, 1.0D, scaleZ, octaveSamplers, octaveCount);
    }
}
