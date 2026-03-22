package com.legacyminecraft.poseidon.compat.bukkit;

/**
 * Canonical behavior for bucket index derivation in CraftBukkit long-hash collections.
 */
public final class LongHashBucketIndexBehaviour {
    private static final LongHashBucketIndexBehaviour INSTANCE = new LongHashBucketIndexBehaviour();

    private LongHashBucketIndexBehaviour() {
    }

    public static LongHashBucketIndexBehaviour getInstance() {
        return INSTANCE;
    }

    public int mainIndex(long key) {
        return (int) (key & 255);
    }

    public int outerIndex(long key) {
        return (int) ((key >> 32) & 255);
    }
}

