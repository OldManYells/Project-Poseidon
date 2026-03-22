package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.PerlinOctaveCompositionBehaviour;

import java.util.Random;

public class NoiseGeneratorOctaves extends NoiseGenerator {
    private static final PerlinOctaveCompositionBehaviour PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR = PerlinOctaveCompositionBehaviour.getInstance();

    private NoiseGeneratorPerlin[] a;
    private int b;

    public NoiseGeneratorOctaves(Random random, int i) {
        this.b = i;
        this.a = PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.createOctaves(random, i);
    }

    public double a(double d0, double d1) {
        return PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample2d(d0, d1, this.a, this.b);
    }

    public double[] a(double[] adouble, double d0, double d1, double d2, int i, int j, int k, double d3, double d4, double d5) {
        return PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample3d(adouble, d0, d1, d2, i, j, k, d3, d4, d5, this.a, this.b);
    }

    public double[] a(double[] adouble, int i, int j, int k, int l, double d0, double d1, double d2) {
        return PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample2dSlice(adouble, i, j, k, l, d0, d1, this.a, this.b);
    }
}
