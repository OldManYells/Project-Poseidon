package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashset add-or-ignore orchestration.
 */
public final class LongHashsetAddBehaviour {
    private static final LongHashsetAddBehaviour INSTANCE = new LongHashsetAddBehaviour();

    private LongHashsetAddBehaviour() {
    }

    public static LongHashsetAddBehaviour getInstance() {
        return INSTANCE;
    }

    public interface AddCallbacks {
        long[][] createOuterBuckets();

        long[] createInnerBucket();

        long[] copyOf(long[] source, int newLength);
    }

    public static final class AddResult {
        private final int updatedCount;
        private final boolean inserted;

        public AddResult(int updatedCount, boolean inserted) {
            this.updatedCount = updatedCount;
            this.inserted = inserted;
        }

        public int updatedCount() {
            return updatedCount;
        }

        public boolean inserted() {
            return inserted;
        }
    }

    public AddResult add(
            long[][][] values,
            int currentCount,
            int mainIndex,
            int outerIndex,
            long key,
            AddCallbacks callbacks
    ) {
        long[][] outer = values[mainIndex];
        if (outer == null) {
            outer = callbacks.createOuterBuckets();
            values[mainIndex] = outer;
        }

        long[] inner = outer[outerIndex];
        if (inner == null) {
            inner = callbacks.createInnerBucket();
            outer[outerIndex] = inner;
            inner[0] = key;
            return new AddResult(currentCount + 1, true);
        }

        int index;
        for (index = 0; index < inner.length; index++) {
            if (inner[index] == key) {
                return new AddResult(currentCount, false);
            }
        }

        inner = callbacks.copyOf(inner, index + 1);
        outer[outerIndex] = inner;
        inner[index] = key;
        return new AddResult(currentCount + 1, true);
    }
}
