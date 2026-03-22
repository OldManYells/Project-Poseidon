package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical infinite sleeper used by legacy keep-alive daemon wrappers.
 */
public final class SleepForeverService {
    private static final SleepForeverService INSTANCE = new SleepForeverService();

    private SleepForeverService() {
    }

    public static SleepForeverService getInstance() {
        return INSTANCE;
    }

    public void sleepForever() {
        while (true) {
            try {
                while (true) {
                    Thread.sleep(2147483647L);
                }
            } catch (InterruptedException interruptedexception) {
                ;
            }
        }
    }
}
