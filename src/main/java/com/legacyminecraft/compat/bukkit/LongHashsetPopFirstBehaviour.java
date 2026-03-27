package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashset first-key pop orchestration.
 */
public final class LongHashsetPopFirstBehaviour {
    private static final LongHashsetPopFirstBehaviour INSTANCE = new LongHashsetPopFirstBehaviour();

    private LongHashsetPopFirstBehaviour() {
    }

    public static LongHashsetPopFirstBehaviour getInstance() {
        return INSTANCE;
    }

    public interface PopFirstCallbacks {
        long[] copyOf(long[] source, int newLength);
    }

    public static final class PopFirstResult {
        private final long poppedValue;
        private final int updatedCount;

        public PopFirstResult(long poppedValue, int updatedCount) {
            this.poppedValue = poppedValue;
            this.updatedCount = updatedCount;
        }

        public long poppedValue() {
            return poppedValue;
        }

        public int updatedCount() {
            return updatedCount;
        }
    }

    public PopFirstResult popFirst(long[][][] values, int currentCount, PopFirstCallbacks callbacks) {
        for (long[][] outer : values) {
            if (outer == null) {
                continue;
            }

            for (int index = 0; index < outer.length; index++) {
                long[] inner = outer[index];
                if (inner == null || inner.length == 0) {
                    continue;
                }

                long poppedValue = inner[inner.length - 1];
                outer[index] = callbacks.copyOf(inner, inner.length - 1);
                return new PopFirstResult(poppedValue, currentCount - 1);
            }
        }

        return new PopFirstResult(0L, currentCount);
    }
}
