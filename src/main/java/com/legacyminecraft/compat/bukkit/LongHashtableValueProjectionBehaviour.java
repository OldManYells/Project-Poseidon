package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;

/**
 * Canonical behavior for projecting values from legacy LongHashtable bucket storage.
 */
public final class LongHashtableValueProjectionBehaviour {
    private static final LongHashtableValueProjectionBehaviour INSTANCE = new LongHashtableValueProjectionBehaviour();

    private LongHashtableValueProjectionBehaviour() {
    }

    public static LongHashtableValueProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public interface ValueExtractor<V> {
        V extract(Object entry);
    }

    public <V> ArrayList<V> values(Object[][][] values, ValueExtractor<V> valueExtractor) {
        ArrayList<V> projectedValues = new ArrayList<V>();

        for (Object[][] outer : values) {
            if (outer == null) {
                continue;
            }

            for (Object[] inner : outer) {
                if (inner == null) {
                    continue;
                }

                for (Object entry : inner) {
                    if (entry == null) {
                        break;
                    }

                    projectedValues.add(valueExtractor.extract(entry));
                }
            }
        }

        return projectedValues;
    }
}
