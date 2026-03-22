package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftChunkWrapperThinnessTest {
    private static final Path CRAFT_CHUNK_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftChunk.java");

    @Test
    public void craftChunkDelegatesCacheAndCollectionOperationsToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_CHUNK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftChunkAccessBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CraftChunkHandleResolutionBehaviour"));
        Assert.assertTrue(text.contains("CRAFT_CHUNK_HANDLE_RESOLUTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveHandle(weakChunk, worldServer, x, z)"));
        Assert.assertTrue(text.contains("resolveBlock(this.cache, this, getX(), getZ(), x, y, z)"));
        Assert.assertTrue(text.contains("collectEntities(getHandle())"));
        Assert.assertTrue(text.contains("collectTileEntityStates(getHandle(), worldServer)"));
        Assert.assertTrue(text.contains("ChunkSnapshotCaptureBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("captureFromChunk("));
        Assert.assertTrue(text.contains("captureEmpty("));
        Assert.assertFalse(text.contains("int pos = (x & 0xF) << 11 | (z & 0xF) << 7 | (y & 0x7F);"));
        Assert.assertFalse(text.contains("for (int i = 0; i < 8; i++)"));
        Assert.assertFalse(text.contains("chunk.entitySlices[i].size()"));
        Assert.assertFalse(text.contains("chunk.tileEntities.keySet().toArray()"));
        Assert.assertFalse(text.contains("weakChunk.get()"));
        Assert.assertFalse(text.contains("worldServer.getChunkAt(x, z)"));
        Assert.assertFalse(text.contains("byte[] buf = new byte[32768 + 16384 + 16384 + 16384];"));
        Assert.assertFalse(text.contains("chunk.getData(buf, 0, 0, 0, 16, 128, 16, 0);"));
        Assert.assertFalse(text.contains("System.arraycopy(wcm.temperature, 0, biomeTemp, 0, biomeTemp.length);"));
    }
}
