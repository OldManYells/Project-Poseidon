package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldChunkUnloadBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_CHUNK_UNLOAD_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldChunkUnloadBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesChunkUnloadOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_CHUNK_UNLOAD_BRIDGE_BEHAVIOUR_PATH);
        String unloadSection = section(craftWorldText, "public boolean unloadChunk(int x, int z) {", "public boolean unloadChunkRequest(int x, int z, boolean safe) {");
        String unloadRequestSection = section(craftWorldText, "public boolean unloadChunkRequest(int x, int z) {", "public boolean unloadChunkRequest(int x, int z, boolean safe) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldChunkUnloadBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_UNLOAD_BRIDGE_BEHAVIOUR.unloadChunk(this, x, z)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_UNLOAD_BRIDGE_BEHAVIOUR.unloadChunk(this, x, z, save)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_CHUNK_UNLOAD_BRIDGE_BEHAVIOUR.unloadChunkRequest(this, x, z)"));

        Assert.assertFalse(unloadSection.contains("return unloadChunk(x, z, true);"));
        Assert.assertFalse(unloadSection.contains("return unloadChunk(x, z, save, false);"));
        Assert.assertFalse(unloadRequestSection.contains("return unloadChunkRequest(x, z, true);"));

        Assert.assertTrue(behaviourText.contains("unloadChunk(CraftWorld craftWorld, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.unloadChunk(x, z, true);"));
        Assert.assertTrue(behaviourText.contains("unloadChunk(CraftWorld craftWorld, int x, int z, boolean save)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.unloadChunk(x, z, save, false);"));
        Assert.assertTrue(behaviourText.contains("unloadChunkRequest(CraftWorld craftWorld, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.unloadChunkRequest(x, z, true);"));
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
