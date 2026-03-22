package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyLongHashCollectionWrapperThinnessTest {
    private static final Path LONG_HASH_SET_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/util/LongHashset.java");
    private static final Path LONG_HASH_TABLE_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/util/LongHashtable.java");

    @Test
    public void longHashCollectionsDelegateBucketIndexMathToCanonicalBehaviour() throws IOException {
        String longHashSetText = read(LONG_HASH_SET_PATH);
        String longHashTableText = read(LONG_HASH_TABLE_PATH);

        Assert.assertTrue(longHashSetText.contains("LongHashBucketIndexBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key)"));
        Assert.assertFalse(longHashSetText.contains("(int) (key & 255)"));
        Assert.assertFalse(longHashSetText.contains("(int) ((key >> 32) & 255)"));

        Assert.assertTrue(longHashTableText.contains("LongHashBucketIndexBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key)"));
        Assert.assertFalse(longHashTableText.contains("(int) (key & 255)"));
        Assert.assertFalse(longHashTableText.contains("(int) ((key >> 32) & 255)"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

