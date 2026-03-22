package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.SimplexNoiseSamplingBehaviour;

import java.util.Random;

public class NoiseGenerator2 {
    private static final SimplexNoiseSamplingBehaviour SIMPLEX_NOISE_SAMPLING_BEHAVIOUR = SimplexNoiseSamplingBehaviour.getInstance();

    private int[] e;
    public double a;
    public double b;
    public double c;

    public NoiseGenerator2() {
        this(new Random());
    }

    public NoiseGenerator2(Random random) {
        this.e = SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.createPermutation(random);
        this.a = SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
        this.b = SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
        this.c = SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset(random);
    }

    public void a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.sample2d(adouble, d0, d1, i, j, d2, d3, d4, this.e, this.a, this.b);
    }
}
