package com.legacyminecraft.poseidon.network;

/**
 * Canonical pause-duration policy for legacy reader/writer loops.
 */
public final class NetworkLoopPausePolicy {
    private static final NetworkLoopPausePolicy INSTANCE = new NetworkLoopPausePolicy();
    private static final long FAST_LOOP_PAUSE_MILLIS = 2L;
    private static final long STANDARD_LOOP_PAUSE_MILLIS = 100L;

    private NetworkLoopPausePolicy() {
    }

    public static NetworkLoopPausePolicy getInstance() {
        return INSTANCE;
    }

    public long fastLoopPauseMillis() {
        return FAST_LOOP_PAUSE_MILLIS;
    }

    public long standardLoopPauseMillis() {
        return STANDARD_LOOP_PAUSE_MILLIS;
    }
}
