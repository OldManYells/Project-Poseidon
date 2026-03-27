package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashset key-removal orchestration.
 */
public final class LongHashsetRemovalBehaviour {
    private static final LongHashsetRemovalBehaviour INSTANCE = new LongHashsetRemovalBehaviour();

    private LongHashsetRemovalBehaviour() {
    }

    public static LongHashsetRemovalBehaviour getInstance() {
        return INSTANCE;
    }

    public interface RemovalCallbacks {
        long[] copyOf(long[] source, int newLength);
    }

    public static final class RemovalResult {
        private final int updatedCount;
        private final boolean removed;

        public RemovalResult(int updatedCount, boolean removed) {
            this.updatedCount = updatedCount;
            this.removed = removed;
        }

        public int updatedCount() {
            return updatedCount;
        }

        public boolean removed() {
            return removed;
        }
    }

    public RemovalResult remove(
            long[][][] values,
            int currentCount,
            int mainIndex,
            int outerIndex,
            long key,
            RemovalCallbacks callbacks
    ) {
        long[][] outer = values[mainIndex];
        if (outer == null) {
            return new RemovalResult(currentCount, false);
        }

        long[] inner = outer[outerIndex];
        if (inner == null) {
            return new RemovalResult(currentCount, false);
        }

        int max = inner.length - 1;
        for (int index = 0; index <= max; index++) {
            if (inner[index] == key) {
                if (index != max) {
                    inner[index] = inner[max];
                }

                outer[outerIndex] = (max == 0 ? null : callbacks.copyOf(inner, max));
                return new RemovalResult(currentCount - 1, true);
            }
        }

        return new RemovalResult(currentCount, false);
    }
}
