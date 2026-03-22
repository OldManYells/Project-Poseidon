package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.OctavedSimplexNoiseBehaviour;

import java.util.Random;

public class NoiseGeneratorOctaves2 extends NoiseGenerator {
    private static final OctavedSimplexNoiseBehaviour OCTAVED_SIMPLEX_NOISE_BEHAVIOUR = OctavedSimplexNoiseBehaviour.getInstance();

    private NoiseGenerator2[] a;
    private int b;

    public NoiseGeneratorOctaves2(Random random, int i) {
        this.b = i;
        this.a = OCTAVED_SIMPLEX_NOISE_BEHAVIOUR.createOctaves(random, i);
    }

    public double[] a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        return this.a(adouble, d0, d1, i, j, d2, d3, d4, 0.5D);
    }

    public double[] a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4, double d5) {
        return OCTAVED_SIMPLEX_NOISE_BEHAVIOUR.sampleOctaved(adouble, d0, d1, i, j, d2, d3, d4, d5, this.a, this.b);
    }
}
