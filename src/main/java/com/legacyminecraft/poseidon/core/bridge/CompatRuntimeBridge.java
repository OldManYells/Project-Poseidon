package com.legacyminecraft.poseidon.core.bridge;

/**
 * Core-owned contract that compatibility layers can implement for runtime adapter behavior.
 */
public interface CompatRuntimeBridge {
    int resolveSpawnProtectionRadius();

    boolean isOperator(String playerName);
}
