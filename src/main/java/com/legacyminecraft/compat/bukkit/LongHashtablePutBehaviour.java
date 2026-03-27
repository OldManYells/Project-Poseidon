package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashtable put orchestration.
 */
public final class LongHashtablePutBehaviour {
    private static final LongHashtablePutBehaviour INSTANCE = new LongHashtablePutBehaviour();

    private LongHashtablePutBehaviour() {
    }

    public static LongHashtablePutBehaviour getInstance() {
        return INSTANCE;
    }

    public interface PutCallbacks {
        Object[][] createOuterBuckets();

        Object[] createInnerBucket();

        Object[] copyOf(Object[] source, int newLength);

        Object createEntry(long key, Object value);

        long entryKey(Object entry);
    }

    public static final class PutResult {
        private final Object cacheEntry;

        public PutResult(Object cacheEntry) {
            this.cacheEntry = cacheEntry;
        }

        public Object cacheEntry() {
            return cacheEntry;
        }
    }

    public PutResult put(
            long key,
            Object value,
            Object[][][] values,
            int mainIndex,
            int outerIndex,
            PutCallbacks callbacks
    ) {
        Object[][] outer = values[mainIndex];
        if (outer == null) {
            outer = callbacks.createOuterBuckets();
            values[mainIndex] = outer;
        }

        Object[] inner = outer[outerIndex];
        Object newEntry = callbacks.createEntry(key, value);
        if (inner == null) {
            inner = callbacks.createInnerBucket();
            outer[outerIndex] = inner;
            inner[0] = newEntry;
            return new PutResult(newEntry);
        }

        int index;
        for (index = 0; index < inner.length; index++) {
            Object currentEntry = inner[index];
            if (currentEntry == null || callbacks.entryKey(currentEntry) == key) {
                inner[index] = newEntry;
                return new PutResult(newEntry);
            }
        }

        inner = callbacks.copyOf(inner, index + index);
        outer[outerIndex] = inner;
        inner[index] = newEntry;
        return new PutResult(newEntry);
    }
}
