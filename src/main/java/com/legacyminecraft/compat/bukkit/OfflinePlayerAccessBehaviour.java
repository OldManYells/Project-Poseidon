package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player access-list and operator state operations.
 */
public final class OfflinePlayerAccessBehaviour {
    private static final OfflinePlayerAccessBehaviour INSTANCE = new OfflinePlayerAccessBehaviour();

    private OfflinePlayerAccessBehaviour() {
    }

    public static OfflinePlayerAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOperator(CraftServer server, String playerName) {
        return server.getHandle().isOp(normalizeName(playerName));
    }

    public void setOperator(CraftServer server, String playerName, boolean operator) {
        if (operator) {
            server.getHandle().e(normalizeName(playerName));
            return;
        }
        server.getHandle().f(normalizeName(playerName));
    }

    public boolean isBanned(CraftServer server, String playerName) {
        return server.getHandle().banByName.contains(normalizeName(playerName));
    }

    public void setBanned(CraftServer server, String playerName, boolean banned) {
        if (banned) {
            server.getHandle().a(normalizeName(playerName));
            return;
        }
        server.getHandle().b(normalizeName(playerName));
    }

    public boolean isWhitelisted(CraftServer server, String playerName) {
        return server.getHandle().e().contains(normalizeName(playerName));
    }

    public void setWhitelisted(CraftServer server, String playerName, boolean whitelisted) {
        if (whitelisted) {
            server.getHandle().k(normalizeName(playerName));
            return;
        }
        server.getHandle().l(normalizeName(playerName));
    }

    private String normalizeName(String playerName) {
        return playerName.toLowerCase();
    }
}

