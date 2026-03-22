package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyConcurrentSoftMapWrapperThinnessTest {
    private static final Path CONCURRENT_SOFT_MAP_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/util/ConcurrentSoftMap.java");

    @Test
    public void concurrentSoftMapDelegatesStrongReferencePromotionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CONCURRENT_SOFT_MAP_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SoftMapStrongReferenceQueueBehaviour"));
        Assert.assertTrue(text.contains("softMapStrongReferenceQueueBehaviour"));
        Assert.assertTrue(text.contains("softMapStrongReferenceQueueBehaviour.promote(strongReferenceQueue, value, strongReferenceSize);"));
        Assert.assertFalse(text.contains("strongReferenceQueue.addFirst(value);"));
        Assert.assertFalse(text.contains("strongReferenceQueue.removeLast();"));
    }
}

