package com.legacyminecraft.poseidon.item;

/**
 * Canonical lightweight Bukkit facade scaffold for migrated item events.
 */
public final class Bukkit {
    private static final BukkitServer SERVER = new BukkitServer();

    private Bukkit() {
    }

    public static BukkitServer getServer() {
        return SERVER;
    }

    public static final class BukkitServer {
        private final BukkitPluginManager pluginManager = new BukkitPluginManager();

        public BukkitPluginManager getPluginManager() {
            return pluginManager;
        }
    }

    public static final class BukkitPluginManager {
        public void callEvent(Object event) {
        }
    }
}
