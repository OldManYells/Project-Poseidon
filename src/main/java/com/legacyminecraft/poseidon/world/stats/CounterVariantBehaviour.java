package com.legacyminecraft.poseidon.world.stats;

/**
 * Canonical initialization hooks for legacy counter variants.
 */
public final class CounterVariantBehaviour {
    private static final CounterVariantBehaviour INSTANCE = new CounterVariantBehaviour();

    private CounterVariantBehaviour() {
    }

    public static CounterVariantBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeUnknownCounter() {
        // Legacy UnknownCounter is currently a marker type.
    }

    public void initializeTimeCounter() {
        // Legacy TimeCounter is currently a marker type.
    }

    public void initializeDistancesCounter() {
        // Legacy DistancesCounter is currently a marker type.
    }
}
