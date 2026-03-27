package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashset key containment lookup.
 */
public final class LongHashsetContainsKeyBehaviour {
    private static final LongHashsetContainsKeyBehaviour INSTANCE = new LongHashsetContainsKeyBehaviour();

    private LongHashsetContainsKeyBehaviour() {
    }

    public static LongHashsetContainsKeyBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean containsKey(long[][][] values, int mainIndex, int outerIndex, long key) {
        long[][] outer = values[mainIndex];
        if (outer == null) {
            return false;
        }

        long[] inner = outer[outerIndex];
        if (inner == null) {
            return false;
        }

        for (long entry : inner) {
            if (entry == key) {
                return true;
            }
        }
        return false;
    }
}
