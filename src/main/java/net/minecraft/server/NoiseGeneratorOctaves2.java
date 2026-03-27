package net.minecraft.server;

import java.util.Random;

public class NoiseGeneratorOctaves2 extends NoiseGenerator {
    private NoiseGenerator2[] a;
    private int b;

    public NoiseGeneratorOctaves2(Random random, int i) {
        this.b = i;
        this.a = new NoiseGenerator2[i];
        for (int index = 0; index < i; ++index) {
            this.a[index] = new NoiseGenerator2(random);
        }
    }

    public double[] a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4) {
        return this.a(adouble, d0, d1, i, j, d2, d3, d4, 0.5D);
    }

    public double[] a(double[] adouble, double d0, double d1, int i, int j, double d2, double d3, double d4, double d5) {
        if (adouble == null || adouble.length < i * j) {
            adouble = new double[i * j];
        } else {
            for (int index = 0; index < adouble.length; ++index) {
                adouble[index] = 0.0D;
            }
        }

        double frequency = 1.0D;
        double amplitude = 1.0D;
        for (int octave = 0; octave < this.b; ++octave) {
            this.a[octave].a(adouble, d0, d1, i, j, d2 * amplitude * frequency, d3 * amplitude * frequency, 0.55D / frequency);
            amplitude *= d4;
            frequency *= d5;
        }

        return adouble;
    }
}
