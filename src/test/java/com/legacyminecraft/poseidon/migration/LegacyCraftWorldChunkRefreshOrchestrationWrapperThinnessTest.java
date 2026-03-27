package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkRefreshOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_CHUNK_REFRESH_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldChunkRefreshOrchestrationBehaviour.java");

    @Test
    public void craftWorldDelegatesChunkRefreshOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_CHUNK_REFRESH_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean refreshChunk(int x, int z) {", "public boolean isChunkInUse(int x, int z) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldChunkRefreshOrchestrationBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_REFRESH_ORCHESTRATION_BEHAVIOUR.refreshChunk("));
        Assert.assertTrue(craftWorldText.contains("new CraftWorldChunkRefreshOrchestrationBehaviour.ChunkRefreshActions()"));
        Assert.assertFalse(section.contains("if (!isChunkLoaded(x, z)) {"));
        Assert.assertFalse(section.contains("CRAFT_WORLD_CHUNK_LIFECYCLE_BEHAVIOUR.notifyChunkRefresh(world, x, z);"));
        Assert.assertFalse(section.contains("return true;"));

        Assert.assertTrue(behaviourText.contains("refreshChunk(int x, int z, ChunkRefreshActions actions)"));
        Assert.assertTrue(behaviourText.contains("if (!actions.isChunkLoaded(x, z)) {"));
        Assert.assertTrue(behaviourText.contains("actions.notifyChunkRefresh(x, z);"));
        Assert.assertTrue(behaviourText.contains("return true;"));
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
