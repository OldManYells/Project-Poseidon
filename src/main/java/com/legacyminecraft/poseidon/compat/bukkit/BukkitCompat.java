package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Server;

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
