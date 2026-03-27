package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for unsupported legacy ConcurrentSoftMap operations.
 */
public final class SoftMapUnsupportedOperationBehaviour {
    private static final SoftMapUnsupportedOperationBehaviour INSTANCE = new SoftMapUnsupportedOperationBehaviour();

    private SoftMapUnsupportedOperationBehaviour() {
    }

    public static SoftMapUnsupportedOperationBehaviour getInstance() {
        return INSTANCE;
    }

    public RuntimeException unsupported(String message) {
        return new UnsupportedOperationException(message);
    }
}
