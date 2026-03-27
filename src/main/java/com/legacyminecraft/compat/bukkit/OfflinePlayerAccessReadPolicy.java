package com.legacyminecraft.compat.bukkit;


/**
 * Canonical policy for offline-player read access checks.
 */
public final class OfflinePlayerAccessReadPolicy {
    private static final OfflinePlayerAccessReadPolicy INSTANCE = new OfflinePlayerAccessReadPolicy();

    private OfflinePlayerAccessReadPolicy() {
    }

    public static OfflinePlayerAccessReadPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isOperator(CraftServer server, String playerName) {
        return server.getHandle().isOp(normalizeName(playerName));
    }

    public boolean isBanned(CraftServer server, String playerName) {
        return server.getHandle().banByName.contains(normalizeName(playerName));
    }

    public boolean isWhitelisted(CraftServer server, String playerName) {
        return server.getHandle().e().contains(normalizeName(playerName));
    }

    private String normalizeName(String playerName) {
        return playerName.toLowerCase();
    }
}
