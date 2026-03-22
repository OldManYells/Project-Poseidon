package com.legacyminecraft.poseidon.runtime;

/**
 * Role-aligned canonical facade for the infinite sleeper loop.
 */
public final class SleepForeverSystem {
    private static final SleepForeverSystem INSTANCE = new SleepForeverSystem();
    private final SleepForeverService delegate = SleepForeverService.getInstance();

    private SleepForeverSystem() {
    }

    public static SleepForeverSystem getInstance() {
        return INSTANCE;
    }

    public void sleepForever() {
        delegate.sleepForever();
    }
}
