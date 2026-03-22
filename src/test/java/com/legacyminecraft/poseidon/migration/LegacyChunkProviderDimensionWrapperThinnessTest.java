package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkProviderDimensionWrapperThinnessTest {
    private static final Path CHUNK_PROVIDER_HELL_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderHell.java");
    private static final Path CHUNK_PROVIDER_SKY_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderSky.java");

    @Test
    public void hellAndSkyProvidersDelegateChunkBootstrapSeedAndBufferToCanonicalBehaviour() throws IOException {
        String hellText = new String(Files.readAllBytes(CHUNK_PROVIDER_HELL_PATH), StandardCharsets.UTF_8);
        String skyText = new String(Files.readAllBytes(CHUNK_PROVIDER_SKY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(hellText.contains("import com.legacyminecraft.poseidon.world.gen.ChunkGeneratorBootstrapBehaviour;"));
        Assert.assertTrue(hellText.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.chunkSeed(i, j)"));
        Assert.assertTrue(hellText.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.createChunkBlockBuffer()"));
        Assert.assertFalse(hellText.contains("this.h.setSeed((long) i * 341873128712L + (long) j * 132897987541L);"));
        Assert.assertFalse(hellText.contains("byte[] abyte = new byte['\\u8000'];"));

        Assert.assertTrue(skyText.contains("import com.legacyminecraft.poseidon.world.gen.ChunkGeneratorBootstrapBehaviour;"));
        Assert.assertTrue(skyText.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.chunkSeed(i, j)"));
        Assert.assertTrue(skyText.contains("CHUNK_GENERATOR_BOOTSTRAP_BEHAVIOUR.createChunkBlockBuffer()"));
        Assert.assertFalse(skyText.contains("this.j.setSeed((long) i * 341873128712L + (long) j * 132897987541L);"));
        Assert.assertFalse(skyText.contains("byte[] abyte = new byte['\\u8000'];"));
    }
}
