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
        Assert.assertTrue(longHashSetText.contains("LongHashsetAddBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LongHashsetContainsKeyBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LongHashsetKeyProjectionBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LongHashsetLockingBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LongHashsetPopFirstBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LongHashsetRemovalBehaviour"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key)"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_LOCKING_BEHAVIOUR.withReadLock("));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_LOCKING_BEHAVIOUR.withWriteLock("));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_ADD_BEHAVIOUR.add("));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_CONTAINS_KEY_BEHAVIOUR.containsKey("));
        Assert.assertTrue(longHashSetText.contains("return LONG_HASHSET_KEY_PROJECTION_BEHAVIOUR.keys(values, count);"));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_POP_FIRST_BEHAVIOUR.popFirst("));
        Assert.assertTrue(longHashSetText.contains("LONG_HASHSET_REMOVAL_BEHAVIOUR.remove("));
        Assert.assertFalse(longHashSetText.contains("rl.lock();"));
        Assert.assertFalse(longHashSetText.contains("wl.lock();"));
        Assert.assertFalse(longHashSetText.contains("(int) (key & 255)"));
        Assert.assertFalse(longHashSetText.contains("(int) ((key >> 32) & 255)"));
        Assert.assertFalse(longHashSetText.contains("this.values[mainIdx] = outer = new long[256][];"));
        Assert.assertFalse(longHashSetText.contains("for (long entry: inner)"));
        Assert.assertFalse(longHashSetText.contains("long ret = inner[inner.length - 1];"));
        Assert.assertFalse(longHashSetText.contains("inner[i] = inner[max];"));

        Assert.assertTrue(longHashTableText.contains("LongHashBucketIndexBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtableChunkKeyValidationBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtableContainsKeyBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtableGetBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtablePutBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtableRemovalBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LongHashtableValueProjectionBehaviour"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key)"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_GET_BEHAVIOUR.get("));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_PUT_BEHAVIOUR.put("));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_CONTAINS_KEY_BEHAVIOUR.containsKey("));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_REMOVAL_BEHAVIOUR.remove("));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_CHUNK_KEY_VALIDATION_BEHAVIOUR.validateChunkCoordinates(msw, lsw, value);"));
        Assert.assertTrue(longHashTableText.contains("LONG_HASHTABLE_VALUE_PROJECTION_BEHAVIOUR.values("));
        Assert.assertFalse(longHashTableText.contains("(int) (key & 255)"));
        Assert.assertFalse(longHashTableText.contains("(int) ((key >> 32) & 255)"));
        Assert.assertFalse(longHashTableText.contains("containsKey(key) ? (V) cache.value : null"));
        Assert.assertFalse(longHashTableText.contains("if (this.cache != null && cache.key == key) return true;"));
        Assert.assertFalse(longHashTableText.contains("outer[outerIdx] = inner = new Object[5];"));
        Assert.assertFalse(longHashTableText.contains("inner[i-1] = inner[i];"));
        Assert.assertFalse(longHashTableText.contains("Chunk (\" + c.x + \", \" + c.z + \") stored at  (\" + msw + \", \" + lsw + \")"));
        Assert.assertFalse(longHashTableText.contains("Throwable x = new Throwable();"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
