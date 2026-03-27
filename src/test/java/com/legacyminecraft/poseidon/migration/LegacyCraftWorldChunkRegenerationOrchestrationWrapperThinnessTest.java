package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkRegenerationOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_CHUNK_REGENERATION_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldChunkRegenerationOrchestrationBehaviour.java");

    @Test
    public void craftWorldDelegatesChunkRegenerationOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_CHUNK_REGENERATION_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean regenerateChunk(int x, int z) {", "public boolean refreshChunk(int x, int z) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldChunkRegenerationOrchestrationBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_REGENERATION_ORCHESTRATION_BEHAVIOUR.regenerateChunk("));
        Assert.assertTrue(craftWorldText.contains("new CraftWorldChunkRegenerationOrchestrationBehaviour.ChunkRegenerationActions()"));
        Assert.assertFalse(section.contains("unloadChunk(x, z, false, false);"));
        Assert.assertFalse(section.contains("resolveRegeneratedChunk(world.chunkProviderServer, x, z)"));
        Assert.assertFalse(section.contains("chunkLoadPostProcess(chunk, x, z);"));
        Assert.assertFalse(section.contains("refreshChunk(x, z);"));
        Assert.assertFalse(section.contains("return chunk != null;"));

        Assert.assertTrue(behaviourText.contains("regenerateChunk(int x, int z, ChunkRegenerationActions actions)"));
        Assert.assertTrue(behaviourText.contains("actions.unloadChunk(x, z, false, false);"));
        Assert.assertTrue(behaviourText.contains("net.minecraft.server.Chunk chunk = actions.resolveRegeneratedChunk(x, z);"));
        Assert.assertTrue(behaviourText.contains("actions.postProcessLoadedChunk(chunk, x, z);"));
        Assert.assertTrue(behaviourText.contains("actions.refreshChunk(x, z);"));
        Assert.assertTrue(behaviourText.contains("return chunk != null;"));
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
