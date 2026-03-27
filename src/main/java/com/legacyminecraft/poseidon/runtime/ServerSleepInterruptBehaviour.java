package com.legacyminecraft.poseidon.runtime;

/**
 * Canonical interrupt handling for server sleep-based loops.
 */
public final class ServerSleepInterruptBehaviour {
    private static final ServerSleepInterruptBehaviour INSTANCE = new ServerSleepInterruptBehaviour();

    private ServerSleepInterruptBehaviour() {
    }

    public static ServerSleepInterruptBehaviour getInstance() {
        return INSTANCE;
    }

    public void preserveInterrupt() {
        Thread.currentThread().interrupt();
    }
}
