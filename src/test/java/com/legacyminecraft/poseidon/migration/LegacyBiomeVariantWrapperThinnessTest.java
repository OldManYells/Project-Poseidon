package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyBiomeVariantWrapperThinnessTest {
    private static final Path BIOME_DESERT_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeDesert.java");
    private static final Path BIOME_SWAMP_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeSwamp.java");

    @Test
    public void biomeDesertAndSwampDelegateInitializationToCanonicalBehaviour() throws IOException {
        String desertText = new String(Files.readAllBytes(BIOME_DESERT_PATH), StandardCharsets.UTF_8);
        String swampText = new String(Files.readAllBytes(BIOME_SWAMP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(desertText.contains("BiomeVariantInitializationBehaviour"));
        Assert.assertTrue(desertText.contains("BIOME_VARIANT_INITIALIZATION_BEHAVIOUR.initializeDesertBiome(this)"));
        Assert.assertFalse(desertText.contains("public BiomeDesert() {}"));

        Assert.assertTrue(swampText.contains("BiomeVariantInitializationBehaviour"));
        Assert.assertTrue(swampText.contains("BIOME_VARIANT_INITIALIZATION_BEHAVIOUR.initializeSwampBiome(this)"));
        Assert.assertFalse(swampText.contains("public BiomeSwamp() {}"));
    }
}
