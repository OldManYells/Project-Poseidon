package org.bukkit.craftbukkit.util;

import com.legacyminecraft.poseidon.compat.bukkit.LongHashBucketIndexBehaviour;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

import static org.bukkit.craftbukkit.util.Java15Compat.Arrays_copyOf;

public class LongHashset extends LongHash {
    private static final LongHashBucketIndexBehaviour LONG_HASH_BUCKET_INDEX_BEHAVIOUR =
            LongHashBucketIndexBehaviour.getInstance();
    long[][][] values = new long[256][][];
    int count = 0;
    ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    ReadLock rl = rwl.readLock();
    WriteLock wl = rwl.writeLock();

    public boolean isEmpty() {
        rl.lock();
        try {
            return this.count == 0;
        } finally {
            rl.unlock();
        }
    }

    public void add(int msw, int lsw) {
        add(toLong(msw, lsw));
    }

    public void add(long key) {
        wl.lock();
        try {
            int mainIdx = LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key);
            long outer[][] = this.values[mainIdx];
            if (outer == null) this.values[mainIdx] = outer = new long[256][];

            int outerIdx = LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key);
            long inner[] = outer[outerIdx];

            if (inner == null) {
                synchronized (this) {
                    outer[outerIdx] = inner = new long[1];
                    inner[0] = key;
                    this.count++;
                }
            } else {
                int i;
                for (i = 0; i < inner.length; i++) {
                    if (inner[i] == key) {
                        return;
                    }
                }
                inner = Arrays_copyOf(inner, i + 1);
                outer[outerIdx] = inner;
                inner[i] = key;
                this.count++;
            }
        } finally {
            wl.unlock();
        }
    }

    public boolean containsKey(long key) {
        rl.lock();
        try {
            long[][] outer = this.values[LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)];
            if (outer == null) return false;

            long[] inner = outer[LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key)];
            if (inner == null) return false;

            for (long entry: inner) {
                if (entry == key) return true;
            }
            return false;
        } finally {
            rl.unlock();
        }
    }

    public void remove(long key) {
        wl.lock();
        try {
            int outerIndex = LONG_HASH_BUCKET_INDEX_BEHAVIOUR.outerIndex(key);
            long[][] outer = this.values[LONG_HASH_BUCKET_INDEX_BEHAVIOUR.mainIndex(key)];
            if (outer == null) return;

            long[] inner = outer[outerIndex];
            if (inner == null) return;

            int max = inner.length - 1;
            for (int i = 0; i <= max; i++) {
                if (inner[i] == key) {
                    this.count--;
                    if (i != max) {
                        inner[i] = inner[max];
                    }

                    outer[outerIndex] = (max == 0 ? null : Arrays_copyOf(inner, max));
                    return;
                }
            }
        } finally {
            wl.unlock();
        }
    }

    public long popFirst() {
        wl.lock();
        try {
            for (long[][] outer: this.values) {
                if (outer == null) continue;

                for (int i = 0; i < outer.length; i++) {
                    long[] inner = outer[i];
                    if (inner == null || inner.length == 0) continue;

                    this.count--;
                    long ret = inner[inner.length - 1];
                    outer[i] = Arrays_copyOf(inner, inner.length - 1);

                    return ret;
                }
            }
        } finally {
            wl.unlock();
        }
        return 0;
    }

    public long[] keys() {
        int index = 0;
        rl.lock();
        try {
            long[] ret = new long[this.count];
            for (long[][] outer: this.values) {
                if (outer == null) continue;

                for (long[] inner: outer) {
                    if (inner == null) continue;

                    for (long entry: inner) {
                        ret[index++] = entry;
                    }
                }
            }
            return ret;
        } finally {
            rl.unlock();
        }
    }
}
