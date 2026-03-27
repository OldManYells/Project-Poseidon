package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for CraftBukkit long-hash key packing/unpacking.
 */
public final class LongHashKeyBehaviour {
    private static final LongHashKeyBehaviour INSTANCE = new LongHashKeyBehaviour();

    private LongHashKeyBehaviour() {
    }

    public static LongHashKeyBehaviour getInstance() {
        return INSTANCE;
    }

    public long toLong(int mostSignificantWord, int leastSignificantWord) {
        return ((long) mostSignificantWord << 32) + leastSignificantWord - Integer.MIN_VALUE;
    }

    public int mostSignificantWord(long combinedKey) {
        return (int) (combinedKey >> 32);
    }

    public int leastSignificantWord(long combinedKey) {
        return (int) (combinedKey & 0xFFFFFFFF) + Integer.MIN_VALUE;
    }
}

