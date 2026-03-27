package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for legacy ConcurrentSoftMap put-if-absent orchestration.
 */
public final class SoftMapPutIfAbsentBehaviour {
    private static final SoftMapPutIfAbsentBehaviour INSTANCE = new SoftMapPutIfAbsentBehaviour();

    private SoftMapPutIfAbsentBehaviour() {
    }

    public static SoftMapPutIfAbsentBehaviour getInstance() {
        return INSTANCE;
    }

    public interface PutIfAbsentCallbacks {
        boolean containsKey(Object key);

        Object get(Object key);

        Object dereference(Object reference);

        Object putIfAbsent(Object key, Object reference);

        boolean replace(Object key, Object oldReference, Object newReference);
    }

    public Object putIfAbsent(Object key, Object newReference, PutIfAbsentCallbacks callbacks) {
        Object resolved = null;

        if (callbacks.containsKey(key)) {
            Object current = callbacks.get(key);
            if (current != null) {
                resolved = callbacks.dereference(current);
            }
        }

        if (resolved == null) {
            boolean success = false;
            while (!success) {
                Object oldReference = callbacks.putIfAbsent(key, newReference);
                if (oldReference == null) {
                    resolved = null;
                    success = true;
                } else {
                    resolved = callbacks.dereference(oldReference);
                    if (resolved == null) {
                        success = callbacks.replace(key, oldReference, newReference);
                    } else {
                        success = true;
                    }
                }
            }
        }

        return resolved;
    }
}
