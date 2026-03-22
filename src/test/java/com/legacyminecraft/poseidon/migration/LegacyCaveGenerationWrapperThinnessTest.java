package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCaveGenerationWrapperThinnessTest {
    private static final Path MAP_GEN_CAVES_PATH = Paths.get("src/main/java/net/minecraft/server/MapGenCaves.java");
    private static final Path MAP_GEN_CAVES_HELL_PATH = Paths.get("src/main/java/net/minecraft/server/MapGenCavesHell.java");

    @Test
    public void mapGenCavesDelegatesCarvingLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(MAP_GEN_CAVES_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("OverworldCaveCarvingBehaviour"));
        Assert.assertTrue(text.contains("OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateLargeCaveNode"));
        Assert.assertTrue(text.contains("OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateCaveNode"));
        Assert.assertTrue(text.contains("OVERWORLD_CAVE_CARVING_BEHAVIOUR.generateChunkCaves"));
        Assert.assertFalse(text.contains("Random random = new Random(this.b.nextLong());"));
        Assert.assertFalse(text.contains("if (j4 < 10) {"));
        Assert.assertFalse(text.contains("if (b0 == Block.STONE.id || b0 == Block.DIRT.id || b0 == Block.GRASS.id)"));
    }

    @Test
    public void mapGenCavesHellDelegatesCarvingLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(MAP_GEN_CAVES_HELL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetherCaveCarvingBehaviour"));
        Assert.assertTrue(text.contains("NETHER_CAVE_CARVING_BEHAVIOUR.generateLargeCaveNode"));
        Assert.assertTrue(text.contains("NETHER_CAVE_CARVING_BEHAVIOUR.generateCaveNode"));
        Assert.assertTrue(text.contains("NETHER_CAVE_CARVING_BEHAVIOUR.generateChunkCaves"));
        Assert.assertFalse(text.contains("Random random = new Random(this.b.nextLong());"));
        Assert.assertFalse(text.contains("if (b0 == Block.NETHERRACK.id || b0 == Block.DIRT.id || b0 == Block.GRASS.id)"));
    }
}
