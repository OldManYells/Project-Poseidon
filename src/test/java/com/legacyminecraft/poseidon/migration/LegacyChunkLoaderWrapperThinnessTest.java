package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkLoaderWrapperThinnessTest {
    private static final Path CHUNK_LOADER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkLoader.java");

    @Test
    public void chunkLoaderDelegatesChunkFilePathResolutionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_LOADER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkFilePathBehaviour;"));
        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.chunk.ChunkNbtLayoutBehaviour;"));
        Assert.assertTrue(text.contains("CHUNK_FILE_PATH_BEHAVIOUR.resolveChunkFile(this.a, this.b, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.hasLevelData(nbttagcompound)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.hasBlockData(levelTag)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.loadChunk(world, levelTag)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.isExpectedChunkLocation(chunk, i, j)"));
        Assert.assertTrue(text.contains("CHUNK_NBT_LAYOUT_BEHAVIOUR.overwriteChunkCoordinates(levelTag, i, j)"));
        Assert.assertFalse(text.contains("String s = \"c.\" + Integer.toString(i, 36) + \".\" + Integer.toString(j, 36) + \".dat\";"));
        Assert.assertFalse(text.contains("String s1 = Integer.toString(i & 63, 36);"));
        Assert.assertFalse(text.contains("String s2 = Integer.toString(j & 63, 36);"));
        Assert.assertFalse(text.contains("if (!nbttagcompound.hasKey(\"Level\"))"));
        Assert.assertFalse(text.contains("if (!nbttagcompound.k(\"Level\").hasKey(\"Blocks\"))"));
    }
}
