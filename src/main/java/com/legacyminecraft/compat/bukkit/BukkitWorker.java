package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat scheduler worker view.
 */
public interface BukkitWorker {
    Plugin getOwner();

    int getTaskId();

    boolean isAlive();

    void interrupt();
}
