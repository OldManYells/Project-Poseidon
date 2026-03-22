package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEmptyChunkWrapperThinnessTest {
    private static final Path EMPTY_CHUNK_PATH = Paths.get("src/main/java/net/minecraft/server/EmptyChunk.java");

    @Test
    public void emptyChunkDelegatesHelperLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(EMPTY_CHUNK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.EmptyChunkBehaviour;"));
        Assert.assertTrue(text.contains("EMPTY_CHUNK_BEHAVIOUR.zeroFillChunkData(abyte, k1, i, j, k, l, i1, j1)"));
        Assert.assertTrue(text.contains("EMPTY_CHUNK_BEHAVIOUR.createChunkRandom(this.world.getSeed(), this.x, this.z, i)"));
        Assert.assertFalse(text.contains("Arrays.fill(abyte, k1, k1 + l2, (byte) 0);"));
        Assert.assertFalse(text.contains("this.x * this.x * 4987142"));
    }
}
