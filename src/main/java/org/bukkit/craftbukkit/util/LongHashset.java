package org.bukkit.craftbukkit.util;

import com.legacyminecraft.compat.bukkit.LongHashBucketIndexBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetAddBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetContainsKeyBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetKeyProjectionBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetLockingBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetPopFirstBehaviour;
import com.legacyminecraft.compat.bukkit.LongHashsetRemovalBehaviour;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import static org.bukkit.craftbukkit.util.Java15Compat.Arrays_copyOf;

public class LongHashset extends LongHash {
    private static final LongHashBucketIndexBehaviour LONG_HASH_BUCKET_INDEX_BEHAVIOUR =
            LongHashBucketIndexBehaviour.getInstance();
    private static final LongHashsetKeyProjectionBehaviour LONG_HASHSET_KEY_PROJECTION_BEHAVIOUR =
            LongHashsetKeyProjectionBehaviour.getInstance();
    private static final LongHashsetContainsKeyBehaviour LONG_HASHSET_CONTAINS_KEY_BEHAVIOUR =
            LongHashsetContainsKeyBehaviour.getInstance();
    private static final LongHashsetAddBehaviour LONG_HASHSET_ADD_BEHAVIOUR =
            LongHashsetAddBehaviour.getInstance();
    private static final LongHashsetPopFirstBehaviour LONG_HASHSET_POP_FIRST_BEHAVIOUR =
            LongHashsetPopFirstBehaviour.getInstance();
    private static final LongHashsetRemovalBehaviour LONG_HASHSET_REMOVAL_BEHAVIOUR =
            LongHashsetRemovalBehaviour.getInstance();
    private static final LongHashsetLockingBehaviour LONG_HASHSET_LOCKING_BEHAVIOUR =
            LongHashsetLockingBehaviour.getInstance();
    long[][][] values = new long[256][][];
    int count = 0;
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    ReadLock rl = rwl.readLock();
    WriteLock wl = rwl.writeLock();

    public boolean isEmpty() {
        return LONG_HASHSET_LOCKING_BEHAVIOUR.withReadLock(rl, new LongHashsetLockingBehaviour.ValueOperation<Boolean>() {
            @Override
            public Boolean execute() {
                return count == 0;
            }
        });
    }

    public void add(int msw, int lsw) {
        add(toLong(msw, lsw));
    }

    public void add(long key) {
        LONG_HASHSET_LOCKING_BEHAVIOUR.withWriteLock(wl, new LongHashsetLockingBehaviour.ValueOperation<Void>() {
            @Override
            public Void execute() {
                LongHashsetAddBehaviour.AddResult result = LONG_HASHSET_ADD_BEHAVIOUR.add(
                        values,
                        count,
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                        key,
                        new LongHashsetAddBehaviour.AddCallbacks() {
                            @Override
                            public long[][] createOuterBuckets() {
                                return new long[256][];
                            }

                            @Override
                            public long[] createInnerBucket() {
                                return new long[1];
                            }

                            @Override
                            public long[] copyOf(long[] source, int newLength) {
                                return Arrays_copyOf(source, newLength);
                            }
                        }
                );
                count = result.updatedCount();
                return null;
            }
        });
    }

    public boolean containsKey(long key) {
        return LONG_HASHSET_LOCKING_BEHAVIOUR.withReadLock(rl, new LongHashsetLockingBehaviour.ValueOperation<Boolean>() {
            @Override
            public Boolean execute() {
                return LONG_HASHSET_CONTAINS_KEY_BEHAVIOUR.containsKey(
                        values,
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                        key
                );
            }
        });
    }

    public void remove(long key) {
        LONG_HASHSET_LOCKING_BEHAVIOUR.withWriteLock(wl, new LongHashsetLockingBehaviour.ValueOperation<Void>() {
            @Override
            public Void execute() {
                LongHashsetRemovalBehaviour.RemovalResult result = LONG_HASHSET_REMOVAL_BEHAVIOUR.remove(
                        values,
                        count,
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key),
                        LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key),
                        key,
                        new LongHashsetRemovalBehaviour.RemovalCallbacks() {
                            @Override
                            public long[] copyOf(long[] source, int newLength) {
                                return Arrays_copyOf(source, newLength);
                            }
                        }
                );
                count = result.updatedCount();
                return null;
            }
        });
    }

    public long popFirst() {
        return LONG_HASHSET_LOCKING_BEHAVIOUR.withWriteLock(wl, new LongHashsetLockingBehaviour.ValueOperation<Long>() {
            @Override
            public Long execute() {
                LongHashsetPopFirstBehaviour.PopFirstResult result = LONG_HASHSET_POP_FIRST_BEHAVIOUR.popFirst(
                        values,
                        count,
                        new LongHashsetPopFirstBehaviour.PopFirstCallbacks() {
                            @Override
                            public long[] copyOf(long[] source, int newLength) {
                                return Arrays_copyOf(source, newLength);
                            }
                        }
                );
                count = result.updatedCount();
                return result.poppedValue();
            }
        });
    }

    public long[] keys() {
        return LONG_HASHSET_LOCKING_BEHAVIOUR.withReadLock(rl, new LongHashsetLockingBehaviour.ValueOperation<long[]>() {
            @Override
            public long[] execute() {
                return LONG_HASHSET_KEY_PROJECTION_BEHAVIOUR.keys(values, count);
            }
        });
    }
}
