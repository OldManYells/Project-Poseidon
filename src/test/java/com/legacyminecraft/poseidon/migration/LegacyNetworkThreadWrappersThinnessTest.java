package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNetworkThreadWrappersThinnessTest {
    private static final Path NETWORK_READER_THREAD_PATH =
            Paths.get("src/main/java/net/minecraft/server/NetworkReaderThread.java");
    private static final Path NETWORK_WRITER_THREAD_PATH =
            Paths.get("src/main/java/net/minecraft/server/NetworkWriterThread.java");
    private static final Path NETWORK_MASTER_THREAD_PATH =
            Paths.get("src/main/java/net/minecraft/server/NetworkMasterThread.java");

    @Test
    public void networkReaderThreadUsesCanonicalSystemNamingAndFieldBackedOperations() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_READER_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkReaderLoopSystem"));
        Assert.assertTrue(text.contains("networkReaderLoopSystem"));
        Assert.assertTrue(text.contains("readerLoopOperations"));
        Assert.assertTrue(text.contains("networkReaderLoopSystem.runLoop(this.fast, this.readerLoopOperations);"));
        Assert.assertFalse(text.contains("networkReaderLoopService"));
    }

    @Test
    public void networkWriterThreadUsesCanonicalSystemNamingAndFieldBackedOperations() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_WRITER_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkWriterLoopSystem"));
        Assert.assertTrue(text.contains("networkWriterLoopSystem"));
        Assert.assertTrue(text.contains("writerLoopOperations"));
        Assert.assertTrue(text.contains("networkWriterLoopSystem.runLoop(this.fast, this.writerLoopOperations);"));
        Assert.assertFalse(text.contains("networkWriterLoopService"));
    }

    @Test
    public void networkMasterThreadUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_MASTER_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkMasterThreadSystem"));
        Assert.assertTrue(text.contains("networkMasterThreadSystem"));
        Assert.assertFalse(text.contains("networkMasterThreadService"));
    }
}
