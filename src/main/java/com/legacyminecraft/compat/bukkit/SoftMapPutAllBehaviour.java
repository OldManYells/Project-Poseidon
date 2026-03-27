package com.legacyminecraft.compat.bukkit;

import java.util.Iterator;
import java.util.Map;

/**
 * Canonical behavior for legacy ConcurrentSoftMap bulk put orchestration.
 */
public final class SoftMapPutAllBehaviour {
    private static final SoftMapPutAllBehaviour INSTANCE = new SoftMapPutAllBehaviour();

    private SoftMapPutAllBehaviour() {
    }

    public static SoftMapPutAllBehaviour getInstance() {
        return INSTANCE;
    }

    public interface PutCallbacks {
        void put(Object key, Object value);
    }

    public void putAll(Map other, PutCallbacks callbacks) {
        Iterator iterator = other.keySet().iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            callbacks.put(key, other.get(key));
        }
    }
}
