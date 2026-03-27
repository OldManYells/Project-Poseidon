package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashtable contains-key lookup and cache update flow.
 */
public final class LongHashtableContainsKeyBehaviour {
    private static final LongHashtableContainsKeyBehaviour INSTANCE = new LongHashtableContainsKeyBehaviour();

    private LongHashtableContainsKeyBehaviour() {
    }

    public static LongHashtableContainsKeyBehaviour getInstance() {
        return INSTANCE;
    }

    public interface ContainsKeyCallbacks {
        boolean isMatchingCache(Object cacheEntry, long key);

        long entryKey(Object entry);
    }

    public static final class ContainsKeyResult {
        private final boolean containsKey;
        private final Object cacheEntry;

        public ContainsKeyResult(boolean containsKey, Object cacheEntry) {
            this.containsKey = containsKey;
            this.cacheEntry = cacheEntry;
        }

        public boolean containsKey() {
            return containsKey;
        }

        public Object cacheEntry() {
            return cacheEntry;
        }
    }

    public ContainsKeyResult containsKey(
            long key,
            Object cacheEntry,
            Object[][][] values,
            int mainIndex,
            int outerIndex,
            ContainsKeyCallbacks callbacks
    ) {
        if (cacheEntry != null && callbacks.isMatchingCache(cacheEntry, key)) {
            return new ContainsKeyResult(true, cacheEntry);
        }

        Object[][] outer = values[mainIndex];
        if (outer == null) {
            return new ContainsKeyResult(false, cacheEntry);
        }

        Object[] inner = outer[outerIndex];
        if (inner == null) {
            return new ContainsKeyResult(false, cacheEntry);
        }

        for (Object entry : inner) {
            if (entry == null) {
                return new ContainsKeyResult(false, cacheEntry);
            }

            if (callbacks.entryKey(entry) == key) {
                return new ContainsKeyResult(true, entry);
            }
        }

        return new ContainsKeyResult(false, cacheEntry);
    }
}
