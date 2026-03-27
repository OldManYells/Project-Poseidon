package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat travel-agent contract.
 */
public interface TravelAgent {
    Location findOrCreate(Location location);
}

