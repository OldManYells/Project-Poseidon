package com.legacyminecraft.poseidon.network;

/**
 * Canonical shutdown watchdog for legacy network reader/writer thread pairs.
 */
public final class NetworkMasterThreadSystem {
    private static final NetworkMasterThreadSystem INSTANCE = new NetworkMasterThreadSystem();
    private final NetworkMasterThreadShutdownPolicy networkMasterThreadShutdownPolicy =
            NetworkMasterThreadShutdownPolicy.getInstance();
    private final NetworkSocketCloseSuppressionPolicy networkSocketCloseSuppressionPolicy =
            NetworkSocketCloseSuppressionPolicy.getInstance();

    private NetworkMasterThreadSystem() {
    }

    public static NetworkMasterThreadSystem getInstance() {
        return INSTANCE;
    }

    public void stopLingeringNetworkThreads(Thread readerThread, Thread writerThread) {
        try {
            Thread.sleep(networkMasterThreadShutdownPolicy.shutdownWaitMillis());
            stopIfAlive(readerThread);
            stopIfAlive(writerThread);
        } catch (InterruptedException interruptedexception) {
            Thread.currentThread().interrupt();
        }
    }

    private void stopIfAlive(Thread thread) {
        if (!networkMasterThreadShutdownPolicy.shouldForceStop(thread)) {
            return;
        }

        try {
            thread.stop();
        } catch (Throwable throwable) {
            networkSocketCloseSuppressionPolicy.suppress(throwable);
        }
    }
}
