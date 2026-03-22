package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for network close-monitor startup sequence.
 */
public final class NetworkCloseMonitorStartSystem {
    private static final NetworkCloseMonitorStartSystem INSTANCE = new NetworkCloseMonitorStartSystem();

    private NetworkCloseMonitorStartSystem() {
    }

    public static NetworkCloseMonitorStartSystem getInstance() {
        return INSTANCE;
    }

    public void start(CloseMonitorActions closeMonitorActions) {
        closeMonitorActions.interruptNetworkThreads();
        closeMonitorActions.markShuttingDown();
        closeMonitorActions.interruptReaderThread();
        closeMonitorActions.startCloseMonitorThread();
    }

    public interface CloseMonitorActions {
        void interruptNetworkThreads();

        void markShuttingDown();

        void interruptReaderThread();

        void startCloseMonitorThread();
    }
}
