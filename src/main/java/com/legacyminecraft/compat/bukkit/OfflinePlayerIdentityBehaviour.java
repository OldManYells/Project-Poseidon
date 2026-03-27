package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for offline-player identity projections.
 */
public final class OfflinePlayerIdentityBehaviour {
    private static final OfflinePlayerIdentityBehaviour INSTANCE = new OfflinePlayerIdentityBehaviour();

    private OfflinePlayerIdentityBehaviour() {
    }

    public static OfflinePlayerIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOnline() {
        return false;
    }

    public String getName(String name) {
        return name;
    }

    public Server getServer(Server server) {
        return server;
    }
}
