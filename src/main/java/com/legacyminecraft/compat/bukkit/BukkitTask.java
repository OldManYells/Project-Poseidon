package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat scheduler task view.
 */
public interface BukkitTask {
    Plugin getOwner();

    int getTaskId();
}
