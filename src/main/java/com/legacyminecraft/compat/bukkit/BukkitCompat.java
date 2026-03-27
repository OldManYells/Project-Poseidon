package com.legacyminecraft.compat.bukkit;


/**
 * Transitional accessors for Bukkit wrappers during migration.
 */
public final class BukkitCompat {
    private BukkitCompat() {
    }

    public static Server getServer() {
        return Bukkit.getServer();
    }
}
