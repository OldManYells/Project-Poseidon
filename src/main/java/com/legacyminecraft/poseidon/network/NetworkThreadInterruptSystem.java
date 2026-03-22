package com.legacyminecraft.poseidon.network;

/**
 * Canonical helper for interrupting legacy network reader/writer threads.
 */
public final class NetworkThreadInterruptSystem {
    private static final NetworkThreadInterruptSystem INSTANCE = new NetworkThreadInterruptSystem();

    private NetworkThreadInterruptSystem() {
    }

    public static NetworkThreadInterruptSystem getInstance() {
        return INSTANCE;
    }

    public void interrupt(Thread readerThread, Thread writerThread) {
        readerThread.interrupt();
        writerThread.interrupt();
    }
}
