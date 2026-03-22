package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyGenerationBaseWrapperThinnessTest {
    private static final Path NOISE_GENERATOR_PATH = Paths.get("src/main/java/net/minecraft/server/NoiseGenerator.java");
    private static final Path WORLD_GENERATOR_PATH = Paths.get("src/main/java/net/minecraft/server/WorldGenerator.java");

    @Test
    public void generationBaseWrappersDelegateBaseHooksToCanonicalBehaviours() throws IOException {
        String noiseText = new String(Files.readAllBytes(NOISE_GENERATOR_PATH), StandardCharsets.UTF_8);
        String worldText = new String(Files.readAllBytes(WORLD_GENERATOR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(noiseText.contains("NoiseGeneratorBaseBehaviour"));
        Assert.assertTrue(noiseText.contains("NOISE_GENERATOR_BASE_BEHAVIOUR.initialize(this)"));

        Assert.assertTrue(worldText.contains("WorldGeneratorBaseBehaviour"));
        Assert.assertTrue(worldText.contains("WORLD_GENERATOR_BASE_BEHAVIOUR.initialize(this)"));
        Assert.assertTrue(worldText.contains("WORLD_GENERATOR_BASE_BEHAVIOUR.configureCoordinateScale(this, d0, d1, d2)"));
        Assert.assertFalse(worldText.contains("public WorldGenerator() {}"));
        Assert.assertFalse(worldText.contains("public void a(double d0, double d1, double d2) {}"));
    }
}
