package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMetadataChunkBlockWrapperThinnessTest {
    private static final Path METADATA_CHUNK_BLOCK_PATH = Paths.get("src/main/java/net/minecraft/server/MetadataChunkBlock.java");

    @Test
    public void metadataChunkBlockDelegatesBoundsMergeToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(METADATA_CHUNK_BLOCK_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.MetadataLightUpdateBoundsBehaviour;"));
        Assert.assertTrue(text.contains("METADATA_LIGHT_UPDATE_BOUNDS_BEHAVIOUR.tryMergeBounds(this, i, j, k, l, i1, j1)"));
        Assert.assertFalse(text.contains("byte b0 = 1;"));
        Assert.assertFalse(text.contains("if (j3 - i3 <= 2)"));
    }
}
