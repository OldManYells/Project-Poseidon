package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for draining stale soft-map references and removing dead keys.
 */
public final class SoftMapReferenceCleanupBehaviour {
    private static final SoftMapReferenceCleanupBehaviour INSTANCE = new SoftMapReferenceCleanupBehaviour();

    private SoftMapReferenceCleanupBehaviour() {
    }

    public static SoftMapReferenceCleanupBehaviour getInstance() {
        return INSTANCE;
    }

    public interface CleanupCallbacks {
        Object pollReference();

        Object extractKey(Object reference);

        void removeByKey(Object key);
    }

    public void drain(CleanupCallbacks callbacks) {
        Object reference;
        while ((reference = callbacks.pollReference()) != null) {
            callbacks.removeByKey(callbacks.extractKey(reference));
        }
    }
}
