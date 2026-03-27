package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashtable key-removal orchestration.
 */
public final class LongHashtableRemovalBehaviour {
    private static final LongHashtableRemovalBehaviour INSTANCE = new LongHashtableRemovalBehaviour();

    private LongHashtableRemovalBehaviour() {
    }

    public static LongHashtableRemovalBehaviour getInstance() {
        return INSTANCE;
    }

    public interface RemovalCallbacks {
        long entryKey(Object entry);
    }

    public static final class RemovalResult {
        private final boolean removed;
        private final boolean clearCache;

        public RemovalResult(boolean removed, boolean clearCache) {
            this.removed = removed;
            this.clearCache = clearCache;
        }

        public boolean removed() {
            return removed;
        }

        public boolean clearCache() {
            return clearCache;
        }
    }

    public RemovalResult remove(
            long key,
            Object[][][] values,
            int mainIndex,
            int outerIndex,
            RemovalCallbacks callbacks
    ) {
        Object[][] outer = values[mainIndex];
        if (outer == null) {
            return new RemovalResult(false, false);
        }

        Object[] inner = outer[outerIndex];
        if (inner == null) {
            return new RemovalResult(false, false);
        }

        for (int index = 0; index < inner.length; index++) {
            Object entry = inner[index];
            if (entry == null) {
                continue;
            }

            if (callbacks.entryKey(entry) == key) {
                int moveIndex = index + 1;
                while (moveIndex < inner.length) {
                    Object movedEntry = inner[moveIndex];
                    if (movedEntry == null) {
                        break;
                    }
                    inner[moveIndex - 1] = movedEntry;
                    moveIndex++;
                }
                inner[moveIndex - 1] = null;
                return new RemovalResult(true, true);
            }
        }

        return new RemovalResult(false, false);
    }
}
