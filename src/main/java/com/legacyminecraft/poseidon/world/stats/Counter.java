package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal formatter contract used by counter statistics.
 */
public interface Counter {
    String format(int value);
}
