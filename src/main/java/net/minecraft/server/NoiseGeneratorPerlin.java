package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.PerlinNoiseSamplingBehaviour;

import java.util.Random;

public class NoiseGeneratorPerlin extends NoiseGenerator {
    private static final PerlinNoiseSamplingBehaviour PERLIN_NOISE_SAMPLING_BEHAVIOUR = PerlinNoiseSamplingBehaviour.getInstance();

    private int[] d;
    public double a;
    public double b;
    public double c;

    public NoiseGeneratorPerlin() {
        this(new Random());
    }

    public NoiseGeneratorPerlin(Random random) {
        this.d = PERLIN_NOISE_SAMPLING_BEHAVIOUR.createPermutation(random);
        this.a = PERLIN_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
        this.b = PERLIN_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
        this.c = PERLIN_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
    }

    public double a(double d0, double d1, double d2) {
        return PERLIN_NOISE_SAMPLING_BEHAVIOUR.sampleSingle(d0, d1, d2, this.d, this.a, this.b, this.c);
    }

    public final double b(double d0, double d1, double d2) {
        return PERLIN_NOISE_SAMPLING_BEHAVIOUR.lerp(d0, d1, d2);
    }

    public final double a(int i, double d0, double d1) {
        return PERLIN_NOISE_SAMPLING_BEHAVIOUR.gradient2d(i, d0, d1);
    }

    public final double a(int i, double d0, double d1, double d2) {
        return PERLIN_NOISE_SAMPLING_BEHAVIOUR.gradient3d(i, d0, d1, d2);
    }

    public double a(double d0, double d1) {
        return this.a(d0, d1, 0.0D);
    }

    public void a(double[] adouble, double d0, double d1, double d2, int i, int j, int k, double d3, double d4, double d5, double d6) {
        PERLIN_NOISE_SAMPLING_BEHAVIOUR.sampleVolume(adouble, d0, d1, d2, i, j, k, d3, d4, d5, d6, this.d, this.a, this.b, this.c);
    }
}
