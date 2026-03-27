package com.legacyminecraft.compat.bukkit;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Canonical behavior for basic legacy ConcurrentSoftMap map/queue access operations.
 */
public final class SoftMapMapAccessBehaviour {
    private static final SoftMapMapAccessBehaviour INSTANCE = new SoftMapMapAccessBehaviour();

    private SoftMapMapAccessBehaviour() {
    }

    public static SoftMapMapAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public void clearStrongReferences(List strongReferenceQueue) {
        strongReferenceQueue.clear();
    }

    public void clearMap(Map map) {
        map.clear();
    }

    public boolean containsKey(Map map, Object key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Map map, Object value) {
        return map.containsValue(value);
    }

    public boolean isEmpty(Map map) {
        return map.isEmpty();
    }

    public Set keySet(Map map) {
        return map.keySet();
    }

    public int size(Map map) {
        return map.size();
    }

    public Object remove(Map map, Object key) {
        return map.remove(key);
    }

    public Object put(Map map, Object key, Object value) {
        return map.put(key, value);
    }

    public boolean replace(Map map, Object key, Object oldValue, Object newValue) {
        return map.replace(key, oldValue, newValue);
    }

    public Object putIfAbsent(Map map, Object key, Object value) {
        return map.putIfAbsent(key, value);
    }
}
