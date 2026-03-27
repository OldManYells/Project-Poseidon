package com.legacyminecraft.compat.bukkit;

import java.lang.reflect.Array;

/**
 * Canonical behavior for legacy Java 1.5-compatible array copy helpers.
 */
public final class JavaArrayCopyBehaviour {
    private static final JavaArrayCopyBehaviour INSTANCE = new JavaArrayCopyBehaviour();

    private JavaArrayCopyBehaviour() {
    }

    public static JavaArrayCopyBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T[] copyOf(T[] original, int newLength) {
        if (0 <= newLength) {
            return java.util.Arrays.copyOf(original, newLength);
        }
        throw new NegativeArraySizeException();
    }

    public long[] copyOf(long[] original, int newLength) {
        if (0 <= newLength) {
            return copyOfRange(original, 0, newLength);
        }
        throw new NegativeArraySizeException();
    }

    private long[] copyOfRange(long[] original, int start, int end) {
        if (original.length >= start && 0 <= start) {
            if (start <= end) {
                int length = end - start;
                int copyLength = Math.min(length, original.length - start);
                long[] copy = (long[]) Array.newInstance(original.getClass().getComponentType(), length);
                System.arraycopy(original, start, copy, 0, copyLength);
                return copy;
            }
            throw new IllegalArgumentException();
        }
        throw new ArrayIndexOutOfBoundsException();
    }
}
