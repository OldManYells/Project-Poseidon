package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyChunkCompressionThreadWrapperThinnessTest {
    private static final Path CHUNK_COMPRESSION_THREAD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/ChunkCompressionThread.java");

    @Test
    public void chunkCompressionThreadDelegatesQueueLifecycleAndRoutingPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CHUNK_COMPRESSION_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ChunkCompressionQueueBehaviour"));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour"));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour.runLoop("));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour.shouldCompress(packet)"));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour.updateQueueSize("));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour.getQueueSize("));
        Assert.assertTrue(text.contains("chunkCompressionQueueBehaviour.enqueueRetry("));
        Assert.assertFalse(text.contains("if (packet instanceof Packet51MapChunk)"));
        Assert.assertFalse(text.contains("synchronized (queueSizePerPlayer)"));
        Assert.assertFalse(text.contains("synchronized (instance.queueSizePerPlayer)"));
        Assert.assertFalse(text.contains("while (true) {"));
        Assert.assertFalse(text.contains("packetQueue.put(task);"));
    }
}

