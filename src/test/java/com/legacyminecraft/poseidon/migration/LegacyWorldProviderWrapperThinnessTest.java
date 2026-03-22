package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldProviderWrapperThinnessTest {
    private static final Path WORLD_PROVIDER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldProvider.java");
    private static final Path WORLD_PROVIDER_HELL_PATH = Paths.get("src/main/java/net/minecraft/server/WorldProviderHell.java");
    private static final Path WORLD_PROVIDER_SKY_PATH = Paths.get("src/main/java/net/minecraft/server/WorldProviderSky.java");

    @Test
    public void worldProviderDelegatesBaseDimensionPoliciesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_PROVIDER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldProviderBehaviour"));
        Assert.assertTrue(text.contains("WORLD_PROVIDER_BEHAVIOUR.buildLightBrightnessTable"));
        Assert.assertTrue(text.contains("WORLD_PROVIDER_BEHAVIOUR.createDefaultChunkManager"));
        Assert.assertTrue(text.contains("WORLD_PROVIDER_BEHAVIOUR.createOverworldChunkProvider"));
        Assert.assertTrue(text.contains("WORLD_PROVIDER_BEHAVIOUR.computeCelestialAngle"));
        Assert.assertTrue(text.contains("WORLD_PROVIDER_BEHAVIOUR.byDimension"));
        Assert.assertFalse(text.contains("new WorldChunkManager(this.a)"));
        Assert.assertFalse(text.contains("new ChunkProviderGenerate(this.a, this.a.getSeed())"));
        Assert.assertFalse(text.contains("Math.cos((double) f1 * 3.141592653589793D)"));
    }

    @Test
    public void worldProviderHellDelegatesNetherPoliciesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_PROVIDER_HELL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("HellWorldProviderBehaviour"));
        Assert.assertTrue(text.contains("HELL_WORLD_PROVIDER_BEHAVIOUR.configure"));
        Assert.assertTrue(text.contains("HELL_WORLD_PROVIDER_BEHAVIOUR.createChunkProvider"));
        Assert.assertTrue(text.contains("HELL_WORLD_PROVIDER_BEHAVIOUR.canSpawn"));
        Assert.assertTrue(text.contains("HELL_WORLD_PROVIDER_BEHAVIOUR.celestialAngle"));
        Assert.assertFalse(text.contains("new WorldChunkManagerHell(BiomeBase.HELL, 1.0D, 0.0D)"));
        Assert.assertFalse(text.contains("new ChunkProviderHell(this.a, this.a.getSeed())"));
        Assert.assertFalse(text.contains("return 0.5F;"));
    }

    @Test
    public void worldProviderSkyDelegatesSkyDimensionPoliciesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_PROVIDER_SKY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SkyWorldProviderBehaviour"));
        Assert.assertTrue(text.contains("SKY_WORLD_PROVIDER_BEHAVIOUR.configure"));
        Assert.assertTrue(text.contains("SKY_WORLD_PROVIDER_BEHAVIOUR.createChunkProvider"));
        Assert.assertTrue(text.contains("SKY_WORLD_PROVIDER_BEHAVIOUR.celestialAngle"));
        Assert.assertTrue(text.contains("SKY_WORLD_PROVIDER_BEHAVIOUR.canSpawn"));
        Assert.assertFalse(text.contains("new WorldChunkManagerHell(BiomeBase.SKY, 0.5D, 0.0D)"));
        Assert.assertFalse(text.contains("new ChunkProviderSky(this.a, this.a.getSeed())"));
        Assert.assertFalse(text.contains("return 0.0F;"));
    }
}
