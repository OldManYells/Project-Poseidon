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
        Assert.assertTrue(text.contains("SoftMapReferenceCleanupBehaviour"));
        Assert.assertTrue(text.contains("SoftMapMapAccessBehaviour"));
        Assert.assertTrue(text.contains("SoftMapPutAllBehaviour"));
        Assert.assertTrue(text.contains("SoftMapPutIfAbsentBehaviour"));
        Assert.assertTrue(text.contains("SoftMapUnsupportedOperationBehaviour"));
        Assert.assertTrue(text.contains("SoftMapValueAccessBehaviour"));
        Assert.assertTrue(text.contains("softMapStrongReferenceQueueBehaviour"));
        Assert.assertTrue(text.contains("softMapReferenceCleanupBehaviour"));
        Assert.assertTrue(text.contains("softMapMapAccessBehaviour"));
        Assert.assertTrue(text.contains("softMapPutAllBehaviour"));
        Assert.assertTrue(text.contains("softMapPutIfAbsentBehaviour"));
        Assert.assertTrue(text.contains("softMapUnsupportedOperationBehaviour"));
        Assert.assertTrue(text.contains("softMapValueAccessBehaviour"));
        Assert.assertTrue(text.contains("softMapStrongReferenceQueueBehaviour.promote(strongReferenceQueue, value, strongReferenceSize);"));
        Assert.assertTrue(text.contains("softMapReferenceCleanupBehaviour.drain("));
        Assert.assertTrue(text.contains("softMapPutAllBehaviour.putAll(other"));
        Assert.assertTrue(text.contains("softMapPutIfAbsentBehaviour.putIfAbsent("));
        Assert.assertTrue(text.contains("softMapUnsupportedOperationBehaviour.unsupported("));
        Assert.assertTrue(text.contains("softMapValueAccessBehaviour.getValue("));
        Assert.assertTrue(text.contains("softMapValueAccessBehaviour.putAndPromote("));
        Assert.assertTrue(text.contains("softMapValueAccessBehaviour.removeAndDereference("));
        Assert.assertFalse(text.contains("while ((ref = (SoftMapReference) queue.poll()) != null)"));
        Assert.assertFalse(text.contains("strongReferenceQueue.addFirst(value);"));
        Assert.assertFalse(text.contains("strongReferenceQueue.removeLast();"));
        Assert.assertFalse(text.contains("Iterator<K> itr = other.keySet().iterator();"));
        Assert.assertFalse(text.contains("SoftMapReference<K, V> ref = map.get(key);"));
        Assert.assertFalse(text.contains("if (ref != null) {"));
    }
}
