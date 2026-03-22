package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNoiseWrapperThinnessTest {
    private static final Path NOISE_GENERATOR2_PATH = Paths.get("src/main/java/net/minecraft/server/NoiseGenerator2.java");
    private static final Path NOISE_GENERATOR_OCTAVES2_PATH = Paths.get("src/main/java/net/minecraft/server/NoiseGeneratorOctaves2.java");
    private static final Path NOISE_GENERATOR_OCTAVES_PATH = Paths.get("src/main/java/net/minecraft/server/NoiseGeneratorOctaves.java");
    private static final Path NOISE_GENERATOR_PERLIN_PATH = Paths.get("src/main/java/net/minecraft/server/NoiseGeneratorPerlin.java");

    @Test
    public void noiseGenerator2DelegatesSimplexSamplingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NOISE_GENERATOR2_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SimplexNoiseSamplingBehaviour"));
        Assert.assertTrue(text.contains("SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.createPermutation"));
        Assert.assertTrue(text.contains("SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset"));
        Assert.assertTrue(text.contains("SIMPLEX_NOISE_SAMPLING_BEHAVIOUR.sample2d"));
        Assert.assertFalse(text.contains("private static int[][] d = new int[][]"));
        Assert.assertFalse(text.contains("private static int a(double d0)"));
        Assert.assertFalse(text.contains("adouble[i3] += 70.0D"));
    }

    @Test
    public void noiseGeneratorOctaves2DelegatesOctaveFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NOISE_GENERATOR_OCTAVES2_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("OctavedSimplexNoiseBehaviour"));
        Assert.assertTrue(text.contains("OCTAVED_SIMPLEX_NOISE_BEHAVIOUR.createOctaves"));
        Assert.assertTrue(text.contains("OCTAVED_SIMPLEX_NOISE_BEHAVIOUR.sampleOctaved"));
        Assert.assertFalse(text.contains("this.a = new NoiseGenerator2[i];"));
        Assert.assertFalse(text.contains("d2 /= 1.5D;"));
        Assert.assertFalse(text.contains("this.a[l].a(adouble, d0, d1, i, j"));
    }

    @Test
    public void noiseGeneratorOctavesDelegatesPerlinOctaveCompositionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NOISE_GENERATOR_OCTAVES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PerlinOctaveCompositionBehaviour"));
        Assert.assertTrue(text.contains("PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.createOctaves"));
        Assert.assertTrue(text.contains("PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample2d"));
        Assert.assertTrue(text.contains("PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample3d"));
        Assert.assertTrue(text.contains("PERLIN_OCTAVE_COMPOSITION_BEHAVIOUR.sample2dSlice"));
        Assert.assertFalse(text.contains("this.a = new NoiseGeneratorPerlin[i];"));
        Assert.assertFalse(text.contains("d2 += this.a[i].a(d0 * d3, d1 * d3) / d3;"));
        Assert.assertFalse(text.contains("this.a[i1].a(adouble, d0, d1, d2, i, j, k"));
    }

    @Test
    public void noiseGeneratorPerlinDelegatesSamplingAndMathToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NOISE_GENERATOR_PERLIN_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PerlinNoiseSamplingBehaviour"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.createPermutation"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.randomCoordinateOffset"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.sampleSingle"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.sampleVolume"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.lerp"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.gradient2d"));
        Assert.assertTrue(text.contains("PERLIN_NOISE_SAMPLING_BEHAVIOUR.gradient3d"));
        Assert.assertFalse(text.contains("this.d = new int[512];"));
        Assert.assertFalse(text.contains("this.d[i + 256] = this.d[i];"));
        Assert.assertFalse(text.contains("return this.b(d8, this.b(d7"));
    }
}
