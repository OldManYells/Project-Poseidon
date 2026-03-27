package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy LongHashtable get-or-null resolution.
 */
public final class LongHashtableGetBehaviour {
    private static final LongHashtableGetBehaviour INSTANCE = new LongHashtableGetBehaviour();

    private LongHashtableGetBehaviour() {
    }

    public static LongHashtableGetBehaviour getInstance() {
        return INSTANCE;
    }

    public Object get(boolean containsKey, Object cacheEntry, ValueExtractor valueExtractor) {
        if (!containsKey) {
            return null;
        }
        return valueExtractor.extract(cacheEntry);
    }

    public interface ValueExtractor {
        Object extract(Object entry);
    }
}
