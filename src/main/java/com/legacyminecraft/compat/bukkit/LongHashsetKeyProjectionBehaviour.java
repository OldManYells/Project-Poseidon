package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for projecting keys from legacy LongHashset storage.
 */
public final class LongHashsetKeyProjectionBehaviour {
    private static final LongHashsetKeyProjectionBehaviour INSTANCE = new LongHashsetKeyProjectionBehaviour();

    private LongHashsetKeyProjectionBehaviour() {
    }

    public static LongHashsetKeyProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public long[] keys(long[][][] values, int count) {
        long[] projectedKeys = new long[count];
        int index = 0;

        for (long[][] outer : values) {
            if (outer == null) {
                continue;
            }

            for (long[] inner : outer) {
                if (inner == null) {
                    continue;
                }

                for (long key : inner) {
                    projectedKeys[index++] = key;
                }
            }
        }

        return projectedKeys;
    }
}
