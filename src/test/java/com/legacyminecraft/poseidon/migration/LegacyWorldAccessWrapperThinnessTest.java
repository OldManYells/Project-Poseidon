package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyWorldAccessWrapperThinnessTest {
    private static final Path WORLD_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldManager.java");
    private static final Path CHUNK_CACHE_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkCache.java");

    @Test
    public void worldManagerDelegatesWorldAccessDispatchToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(WORLD_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("WorldAccessDispatchBehaviour"));
        Assert.assertTrue(text.contains("WORLD_ACCESS_DISPATCH_BEHAVIOUR.onEntityAdded"));
        Assert.assertTrue(text.contains("WORLD_ACCESS_DISPATCH_BEHAVIOUR.onEntityRemoved"));
        Assert.assertTrue(text.contains("WORLD_ACCESS_DISPATCH_BEHAVIOUR.markBlockDirty"));
        Assert.assertTrue(text.contains("WORLD_ACCESS_DISPATCH_BEHAVIOUR.onTileEntityChanged"));
        Assert.assertTrue(text.contains("WORLD_ACCESS_DISPATCH_BEHAVIOUR.sendAuxSfx"));
        Assert.assertFalse(text.contains("this.server.getTracker(this.world.dimension).track(entity)"));
        Assert.assertFalse(text.contains("this.server.serverConfigurationManager.flagDirty(i, j, k, this.world.dimension)"));
        Assert.assertFalse(text.contains("new Packet61(i, j, k, l, i1)"));
    }

    @Test
    public void chunkCacheDelegatesChunkWindowLookupToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_CACHE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChunkWindowAccessBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_WINDOW_ACCESS_BEHAVIOUR.initialize"));
        Assert.assertTrue(text.contains("CHUNK_WINDOW_ACCESS_BEHAVIOUR.getTypeId"));
        Assert.assertTrue(text.contains("CHUNK_WINDOW_ACCESS_BEHAVIOUR.getTileEntity"));
        Assert.assertTrue(text.contains("CHUNK_WINDOW_ACCESS_BEHAVIOUR.getData"));
        Assert.assertFalse(text.contains("this.a = i >> 4"));
        Assert.assertFalse(text.contains("int l = (i >> 4) - this.a"));
    }
}
