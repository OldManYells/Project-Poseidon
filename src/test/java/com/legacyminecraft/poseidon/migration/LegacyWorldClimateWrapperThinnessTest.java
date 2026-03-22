package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldClimateWrapperThinnessTest {
    private static final Path WORLD_CHUNK_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldChunkManager.java");
    private static final Path WORLD_CHUNK_MANAGER_HELL_PATH = Paths.get("src/main/java/net/minecraft/server/WorldChunkManagerHell.java");

    @Test
    public void worldChunkManagerDelegatesDynamicClimateAndBiomeSamplingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_CHUNK_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldChunkClimateBehaviour"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.createTemperatureNoise"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.createHumidityNoise"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.createBlendNoise"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleTemperatureMap"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleBiomeClimate"));
        Assert.assertTrue(text.contains("WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleHumidity"));
        Assert.assertFalse(text.contains("this.e = new NoiseGeneratorOctaves2(new Random(world.getSeed() * 9871L), 4);"));
        Assert.assertFalse(text.contains("double d0 = this.c[i1] * 1.1D + 0.5D;"));
        Assert.assertFalse(text.contains("abiomebase[i1++] = BiomeBase.a(d3, d4);"));
        Assert.assertFalse(text.contains("this.f.a(this.rain, (double)x, (double)z, 1, 1"));
    }

    @Test
    public void worldChunkManagerHellDelegatesFixedBiomeClimateLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_CHUNK_MANAGER_HELL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("FixedBiomeClimateBehaviour"));
        Assert.assertTrue(text.contains("FIXED_BIOME_CLIMATE_BEHAVIOUR.biomeAt"));
        Assert.assertTrue(text.contains("FIXED_BIOME_CLIMATE_BEHAVIOUR.fillTemperatureArray"));
        Assert.assertTrue(text.contains("FIXED_BIOME_CLIMATE_BEHAVIOUR.fillClimate"));
        Assert.assertFalse(text.contains("Arrays.fill(adouble, 0, k * l, this.f)"));
        Assert.assertFalse(text.contains("Arrays.fill(abiomebase, 0, k * l, this.e)"));
        Assert.assertFalse(text.contains("Arrays.fill(this.rain, 0, k * l, this.g)"));
    }
}
