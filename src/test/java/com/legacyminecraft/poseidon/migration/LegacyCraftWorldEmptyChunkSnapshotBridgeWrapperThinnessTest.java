package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldEmptyChunkSnapshotBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_EMPTY_CHUNK_SNAPSHOT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldEmptyChunkSnapshotBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesEmptyChunkSnapshotBridgeWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_EMPTY_CHUNK_SNAPSHOT_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText,
                "public ChunkSnapshot getEmptyChunkSnapshot(int x, int z, boolean includeBiome, boolean includeBiomeTempRain) {",
                "public void setSpawnFlags(boolean allowMonsters, boolean allowAnimals) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldEmptyChunkSnapshotBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EMPTY_CHUNK_SNAPSHOT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(craftWorldText.contains(".getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);"));
        Assert.assertFalse(section.contains("CraftChunk.getEmptyChunkSnapshot(x, z, this, includeBiome, includeBiomeTempRain);"));

        Assert.assertTrue(behaviourText.contains("getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain)"));
        Assert.assertTrue(behaviourText.contains("return CraftChunk.getEmptyChunkSnapshot(x, z, world, includeBiome, includeBiomeTempRain);"));
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
