package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldBiomeLookupWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_BIOME_LOOKUP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldBiomeLookupBehaviour.java");

    @Test
    public void craftWorldDelegatesBiomeLookupWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_BIOME_LOOKUP_BEHAVIOUR_PATH);
        String biomeSection = section(craftWorldText, "public Biome getBiome(int x, int z) {", "public double getTemperature(int x, int z) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldBiomeLookupBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_BIOME_LOOKUP_BEHAVIOUR.getBiome(world, x, z, BIOME_CONVERSION_BEHAVIOUR)"));
        Assert.assertFalse(biomeSection.contains("BiomeBase base = getHandle().getWorldChunkManager().getBiome(x, z);"));
        Assert.assertFalse(biomeSection.contains("return BIOME_CONVERSION_BEHAVIOUR.biomeBaseToBiome(base);"));

        Assert.assertTrue(behaviourText.contains("getBiome(WorldServer worldServer, int x, int z, BiomeConversionBehaviour biomeConversionBehaviour)"));
        Assert.assertTrue(behaviourText.contains("BiomeBase base = worldServer.getWorldChunkManager().getBiome(x, z);"));
        Assert.assertTrue(behaviourText.contains("return biomeConversionBehaviour.biomeBaseToBiome(base);"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
