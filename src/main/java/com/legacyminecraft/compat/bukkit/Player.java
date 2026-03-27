package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * Canonical player facade used by migrated Bukkit bridge behaviour.
 */
public interface Player {
    UUID getUniqueId();

    default String getName() {
        return "player";
    }

    default Object getWorld() {
        return null;
    }

    default Location getLocation() {
        return new Location(null, 0.0D, 0.0D, 0.0D);
    }

    default Location getEyeLocation() {
        return getLocation();
    }

    default void teleport(Location location) {
    }

    default void sendMessage(String message) {
    }

    default boolean isOnline() {
        return true;
    }

    default boolean isInsideVehicle() {
        return false;
    }

    default void recalculatePermissions() {
    }
}
