package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat vehicle wrapper scaffold.
 */
public class CraftVehicle extends CraftEntity implements Vehicle {
    public CraftVehicle(CraftServer server, Entity handle) {
        super(handle);
    }
}
