package com.legacyminecraft.poseidon.world.player;

/**
 * World-player local location alias.
 */
public class Location extends com.legacyminecraft.compat.bukkit.Location {
    public Location(Object world, double x, double y, double z) {
        super(world, x, y, z);
    }

    public Location(Object world, double x, double y, double z, float yaw, float pitch) {
        super(world, x, y, z, yaw, pitch);
    }
}

