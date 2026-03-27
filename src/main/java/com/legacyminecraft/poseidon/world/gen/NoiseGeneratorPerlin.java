package com.legacyminecraft.poseidon.world.gen;

import java.util.Random;

/**
 * Lightweight Perlin-like noise scaffold used by octave composition code.
 */
public class NoiseGeneratorPerlin {
    private final Random random;

    public NoiseGeneratorPerlin(Random random) {
        this.random = random == null ? new Random() : random;
    }

    public double a(double x, double z) {
        long seed = Double.doubleToLongBits(x * 49632.0D + z * 325176.0D);
        seed ^= random.nextLong();
        seed = seed * 2862933555777941757L + 3037000493L;
        return ((seed >>> 24) & 1023L) / 511.5D - 1.0D;
    }

    public void a(double[] output, double startX, double startY, double startZ, int sizeX, int sizeY, int sizeZ, double scaleX, double scaleY, double scaleZ, double amplitude) {
        int index = 0;
        for (int x = 0; x < sizeX; ++x) {
            for (int y = 0; y < sizeY; ++y) {
                for (int z = 0; z < sizeZ; ++z) {
                    double sample = a(startX + (double) x * scaleX, startZ + (double) z * scaleZ);
                    sample += a(startY + (double) y * scaleY, startX + (double) x * scaleX);
                    output[index++] += sample * amplitude;
                }
            }
        }
    }
}
