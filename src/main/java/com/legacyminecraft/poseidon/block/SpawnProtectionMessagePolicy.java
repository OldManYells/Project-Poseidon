package com.legacyminecraft.poseidon.block;

/**
 * Canonical policy for spawn-protection denial feedback in block interaction flows.
 */
public final class SpawnProtectionMessagePolicy {
    private static final SpawnProtectionMessagePolicy INSTANCE = new SpawnProtectionMessagePolicy();

    private SpawnProtectionMessagePolicy() {
    }

    public static SpawnProtectionMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String spawnProtectionDeniedMessage() {
        return "You cannot build within spawn protection.";
    }

    public int messageCooldownTicks() {
        return 20;
    }
}
