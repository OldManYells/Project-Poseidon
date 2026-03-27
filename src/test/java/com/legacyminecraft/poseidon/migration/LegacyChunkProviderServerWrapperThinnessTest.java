package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkProviderServerWrapperThinnessTest {
    private static final Path CHUNK_PROVIDER_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/ChunkProviderServer.java");

    @Test
    public void chunkProviderServerRoutesChunkFailuresThroughCanonicalLogBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_PROVIDER_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChunkLoadFailureLogBehaviour"));
        Assert.assertTrue(text.contains("CHUNK_LOAD_FAILURE_LOG_BEHAVIOUR.logCorruptChunkRegeneration"));
        Assert.assertTrue(text.contains("CHUNK_LOAD_FAILURE_LOG_BEHAVIOUR.logCorruptChunkFailure"));
        Assert.assertTrue(text.contains("CHUNK_LOAD_FAILURE_LOG_BEHAVIOUR.logChunkCoordinateMismatch"));
        Assert.assertTrue(text.contains("CHUNK_LOAD_FAILURE_LOG_BEHAVIOUR.logChunkIoFailure"));
        Assert.assertFalse(text.contains("System.out.println("));
        Assert.assertFalse(text.contains(".printStackTrace();"));
    }
}
