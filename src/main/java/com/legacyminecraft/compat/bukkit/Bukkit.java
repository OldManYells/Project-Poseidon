package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat Bukkit entrypoint scaffold.
 */
public final class Bukkit {
    private static final Server SERVER = new Server();
    private static final BukkitScheduler SCHEDULER = new CraftScheduler();

    private Bukkit() {
    }

    public static Server getServer() {
        return SERVER;
    }

    public static PluginManager getPluginManager() {
        return SERVER.getPluginManager();
    }

    public static BukkitScheduler getScheduler() {
        return SCHEDULER;
    }
}
