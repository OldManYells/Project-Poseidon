package com.legacyminecraft.poseidon.network;

/**
 * Canonical suppression policy for best-effort network socket setup/teardown operations.
 */
public final class NetworkSocketCloseSuppressionPolicy {
    private static final NetworkSocketCloseSuppressionPolicy INSTANCE = new NetworkSocketCloseSuppressionPolicy();

    private NetworkSocketCloseSuppressionPolicy() {
    }

    public static NetworkSocketCloseSuppressionPolicy getInstance() {
        return INSTANCE;
    }

    public void suppress(Throwable ignored) {
        // Intentionally no-op to preserve legacy "quiet close" behavior.
    }
}
