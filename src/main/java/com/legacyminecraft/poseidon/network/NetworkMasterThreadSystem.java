package com.legacyminecraft.poseidon.network;

/**
 * Canonical shutdown watchdog for legacy network reader/writer thread pairs.
 */
public final class NetworkMasterThreadSystem {
    private static final NetworkMasterThreadSystem INSTANCE = new NetworkMasterThreadSystem();

    private NetworkMasterThreadSystem() {
    }

    public static NetworkMasterThreadSystem getInstance() {
        return INSTANCE;
    }

    public void stopLingeringNetworkThreads(Thread readerThread, Thread writerThread) {
        try {
            Thread.sleep(5000L);
            stopIfAlive(readerThread);
            stopIfAlive(writerThread);
        } catch (InterruptedException interruptedexception) {
            interruptedexception.printStackTrace();
        }
    }

    private void stopIfAlive(Thread thread) {
        if (!thread.isAlive()) {
            return;
        }

        try {
            thread.stop();
        } catch (Throwable throwable) {
            ;
        }
    }
}
