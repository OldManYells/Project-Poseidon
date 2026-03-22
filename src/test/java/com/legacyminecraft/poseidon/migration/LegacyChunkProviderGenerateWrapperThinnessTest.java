package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkProviderGenerateWrapperThinnessTest {
    private static final Path CHUNK_PROVIDER_GENERATE_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderGenerate.java");

    @Test
    public void chunkProviderGenerateDelegatesSeedAndBufferBootstrapToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_PROVIDER_GENERATE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.world.gen.ChunkGeneratorBootstrapBehaviour;"));
        Assert.assertTrue(text.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.chunkSeed(i, j)"));
        Assert.assertTrue(text.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.createChunkBlockBuffer()"));
        Assert.assertFalse(text.contains("this.j.setSeed((long) i * 341873128712L + (long) j * 132897987541L);"));
        Assert.assertFalse(text.contains("byte[] abyte = new byte['\\u8000'];"));
    }
}
