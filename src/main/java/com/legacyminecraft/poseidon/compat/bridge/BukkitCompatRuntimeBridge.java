package com.legacyminecraft.poseidon.compat.bridge;

import com.legacyminecraft.poseidon.core.bridge.CompatRuntimeBridge;

/**
 * Compatibility-layer implementation of core runtime bridge contracts.
 */
public final class BukkitCompatRuntimeBridge implements CompatRuntimeBridge {
    private static final BukkitCompatRuntimeBridge INSTANCE = new BukkitCompatRuntimeBridge();

    private BukkitCompatRuntimeBridge() {
    }

    public static BukkitCompatRuntimeBridge getInstance() {
        return INSTANCE;
    }

    @Override
    public int resolveSpawnProtectionRadius() {
        Server server = Bukkit.getServer();
        if (server == null) {
            return 0;
        }
        return server.getSpawnRadius();
    }

    @Override
    public boolean isOperator(String playerName) {
        Server server = Bukkit.getServer();
        if (server == null) {
            return false;
        }
        return server.getOfflinePlayer(playerName).isOp();
    }
}
