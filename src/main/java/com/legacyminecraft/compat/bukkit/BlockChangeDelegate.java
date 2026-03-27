package com.legacyminecraft.compat.bukkit;

/**
 * Block mutation delegate used by world generator wrappers.
 */
public interface BlockChangeDelegate {
    default void setTypeId(int x, int y, int z, int typeId) {
    }
}
