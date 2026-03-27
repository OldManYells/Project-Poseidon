package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkSafeUnloadOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_CHUNK_SAFE_UNLOAD_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldChunkSafeUnloadOrchestrationBehaviour.java");

    @Test
    public void craftWorldDelegatesSafeChunkUnloadOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_CHUNK_SAFE_UNLOAD_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean unloadChunkRequest(int x, int z, boolean safe) {", "public boolean regenerateChunk(int x, int z) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldChunkSafeUnloadOrchestrationBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_SAFE_UNLOAD_ORCHESTRATION_BEHAVIOUR.unloadChunkRequest("));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_SAFE_UNLOAD_ORCHESTRATION_BEHAVIOUR.unloadChunk("));
        Assert.assertFalse(section.contains("canUnloadChunk("));
        Assert.assertFalse(section.contains("queueUnload(world.chunkProviderServer, x, z)"));
        Assert.assertFalse(section.contains("unloadChunk(world.chunkProviderServer, x, z, save)"));

        Assert.assertTrue(behaviourText.contains("unloadChunkRequest(Player[] onlinePlayers, ChunkProviderServer chunkProviderServer, World world, int x, int z, boolean safe, int safeRadius)"));
        Assert.assertTrue(behaviourText.contains("canUnloadChunk("));
        Assert.assertTrue(behaviourText.contains("queueUnload(chunkProviderServer, x, z)"));
        Assert.assertTrue(behaviourText.contains("unloadChunk(Player[] onlinePlayers, ChunkProviderServer chunkProviderServer, World world, int x, int z, boolean save, boolean safe, int safeRadius)"));
        Assert.assertTrue(behaviourText.contains("unloadChunk(chunkProviderServer, x, z, save)"));
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
