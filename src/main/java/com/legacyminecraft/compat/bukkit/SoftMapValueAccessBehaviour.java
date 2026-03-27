package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for ConcurrentSoftMap value read/write/remove access flow.
 */
public final class SoftMapValueAccessBehaviour {
    private static final SoftMapValueAccessBehaviour INSTANCE = new SoftMapValueAccessBehaviour();

    private SoftMapValueAccessBehaviour() {
    }

    public static SoftMapValueAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public Object getValue(Object key, GetCallbacks callbacks) {
        Object reference = callbacks.getReference(key);
        if (reference == null) {
            return null;
        }

        Object value = callbacks.dereference(reference);
        if (value != null) {
            callbacks.promote(value);
        }
        return value;
    }

    public void putAndPromote(Object key, Object reference, Object value, PutCallbacks callbacks) {
        callbacks.put(key, reference);
        callbacks.promote(value);
    }

    public Object removeAndDereference(Object key, RemoveCallbacks callbacks) {
        Object removedReference = callbacks.remove(key);
        if (removedReference == null) {
            return null;
        }
        return callbacks.dereference(removedReference);
    }

    public interface GetCallbacks {
        Object getReference(Object key);

        Object dereference(Object reference);

        void promote(Object value);
    }

    public interface PutCallbacks {
        void put(Object key, Object reference);

        void promote(Object value);
    }

    public interface RemoveCallbacks {
        Object remove(Object key);

        Object dereference(Object reference);
    }
}
