package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkLoadBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_CHUNK_LOAD_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldChunkLoadBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesChunkLoadOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_CHUNK_LOAD_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public void loadChunk(int x, int z) {", "public boolean unloadChunk(Chunk chunk) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldChunkLoadBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_LOAD_BRIDGE_BEHAVIOUR.loadChunk(this, x, z)"));
        Assert.assertFalse(section.contains("loadChunk(x, z, true);"));

        Assert.assertTrue(behaviourText.contains("loadChunk(CraftWorld craftWorld, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("craftWorld.loadChunk(x, z, true);"));
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
