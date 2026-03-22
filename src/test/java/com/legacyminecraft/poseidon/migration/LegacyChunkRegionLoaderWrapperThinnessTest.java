package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkRegionLoaderWrapperThinnessTest {
    private static final Path CHUNK_REGION_LOADER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkRegionLoader.java");

    @Test
    public void chunkRegionLoaderDelegatesChunkNbtValidationToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_REGION_LOADER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkNbtLayoutBehaviour;"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.hasLevelData(nbttagcompound)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.hasBlockData(nbttagcompound.k(\"Level\"))"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.loadChunk(world, levelTag)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.isExpectedChunkLocation(chunk, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.overwriteChunkCoordinates(levelTag, i, j)"));
        Assert.assertFalse(text.contains("if (!nbttagcompound.hasKey(\"Level\"))"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"xPos\", i);"));
        Assert.assertFalse(text.contains("nbttagcompound.a(\"zPos\", j);"));
    }
}
