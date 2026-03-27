package org.bukkit.craftbukkit.util;

import com.legacyminecraft.compat.bukkit.LongHashBucketIndexBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtableChunkKeyValidationBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtableContainsKeyBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtableGetBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtablePutBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtableRemovalBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashtableValueProjectionBehaviour;

import java.util.ArrayList;

import static org.bukkit.craftbukkit.util.Java15Compat.Arrays_copyOf;

public class LongHashtable<V> extends LongHash {
    private static final LongHashBucketIndexBehaviour LONG_HASH_BUCKET_INDEX_BEHAVIOUR =
            LongHashBucketIndexBehaviour.getInstance();
    private static final LongHashtableChunkKeyValidationBehaviour LONG_HASHTABLE_CHUNK_KEY_VALIDATION_BEHAVIOUR =
            LongHashtableChunkKeyValidationBehaviour.getInstance();
    private static final LongHashtableContainsKeyBehaviour LONG_HASHTABLE_CONTAINS_KEY_BEHAVIOUR =
            LongHashtableContainsKeyBehaviour.getInstance();
    private static final LongHashtableGetBehaviour LONG_HASHTABLE_GET_BEHAVIOUR =
            LongHashtableGetBehaviour.getInstance();
    private static final LongHashtablePutBehaviour LONG_HASHTABLE_PUT_BEHAVIOUR =
            LongHashtablePutBehaviour.getInstance();
    private static final LongHashtableRemovalBehaviour LONG_HASHTABLE_REMOVAL_BEHAVIOUR =
            LongHashtableRemovalBehaviour.getInstance();
    private static final LongHashtableValueProjectionBehaviour LONG_HASHTABLE_VALUE_PROJECTION_BEHAVIOUR =
            LongHashtableValueProjectionBehaviour.getInstance();
    Object[][][] values = new Object[256][][];
    Entry cache = null;

    public void put(int msw, int lsw, V value) {
        put(toLong(msw, lsw), value);
        LONG_HASHTABLE_CHUNK_KEY_VALIDATION_BEHAVIOUR.validateChunkCoordinates(msw, lsw, value);
    }

    public V get(int msw, int lsw) {
        V value = get(toLong(msw, lsw));
        LONG_HASHTABLE_CHUNK_KEY_VALIDATION_BEHAVIOUR.validateChunkCoordinates(msw, lsw, value);
        return value;
    }

    public synchronized void put(long key, V value) {
        LongHashtablePutBehaviour.PutResult result = LONG_HASHTABLE_PUT_BEHAVIOUR.put(
                key,
                value,
                this.values,
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                new LongHashtablePutBehaviour.PutCallbacks() {
                    @Override
                    public Object[][] createOuterBuckets() {
                        return new Object[256][];
                    }

                    @Override
                    public Object[] createInnerBucket() {
                        return new Object[5];
                    }

                    @Override
                    public Object[] copyOf(Object[] source, int newLength) {
                        return Arrays_copyOf(source, newLength);
                    }

                    @Override
                    public Object createEntry(long entryKey, Object entryValue) {
                        return new Entry(entryKey, entryValue);
                    }

                    @Override
                    public long entryKey(Object entry) {
                        return ((Entry) entry).key;
                    }
                }
        );
        this.cache = (Entry) result.cacheEntry();
    }

    public synchronized V get(long key) {
        return (V) LONG_HASHTABLE_GET_BEHAVIOUR.get(
                containsKey(key),
                this.cache,
                new LongHashtableGetBehaviour.ValueExtractor() {
                    @Override
                    public Object extract(Object entry) {
                        return ((Entry) entry).value;
                    }
                }
        );
    }

    public synchronized boolean containsKey(long key) {
        LongHashtableContainsKeyBehaviour.ContainsKeyResult result = LONG_HASHTABLE_CONTAINS_KEY_BEHAVIOUR.containsKey(
                key,
                this.cache,
                this.values,
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                new LongHashtableContainsKeyBehaviour.ContainsKeyCallbacks() {
                    @Override
                    public boolean isMatchingCache(Object cacheEntry, long lookupKey) {
                        return ((Entry) cacheEntry).key == lookupKey;
                    }

                    @Override
                    public long entryKey(Object entry) {
                        return ((Entry) entry).key;
                    }
                }
        );
        if (result.containsKey()) {
            this.cache = (Entry) result.cacheEntry();
        }
        return result.containsKey();
    }

    public synchronized void remove(long key) {
        LongHashtableRemovalBehaviour.RemovalResult result = LONG_HASHTABLE_REMOVAL_BEHAVIOUR.remove(
                key,
                this.values,
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                new LongHashtableRemovalBehaviour.RemovalCallbacks() {
                    @Override
                    public long entryKey(Object entry) {
                        return ((Entry) entry).key;
                    }
                }
        );
        if (result.clearCache()) {
            this.cache = null;
        }
    }

    public synchronized ArrayList<V> values() {
        return LONG_HASHTABLE_VALUE_PROJECTION_BEHAVIOUR.values(
                this.values,
                new LongHashtableValueProjectionBehaviour.ValueExtractor<V>() {
                    @Override
                    public V extract(Object entry) {
                        return (V) ((Entry) entry).value;
                    }
                }
        );
    }

    private class Entry {
        long key;
        Object value;

        Entry(long k, Object v) {
            this.key = k;
            this.value = v;
        }
    }
}
