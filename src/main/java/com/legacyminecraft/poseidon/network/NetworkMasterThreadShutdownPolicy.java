package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for network master-thread shutdown timing and force-stop decisions.
 */
public final class NetworkMasterThreadShutdownPolicy {
    private static final NetworkMasterThreadShutdownPolicy INSTANCE = new NetworkMasterThreadShutdownPolicy();
    private static final long SHUTDOWN_WAIT_MILLIS = 5000L;

    private NetworkMasterThreadShutdownPolicy() {
    }

    public static NetworkMasterThreadShutdownPolicy getInstance() {
        return INSTANCE;
    }

    public long shutdownWaitMillis() {
        return SHUTDOWN_WAIT_MILLIS;
    }

    public boolean shouldForceStop(Thread thread) {
        return thread != null && thread.isAlive();
    }
}
