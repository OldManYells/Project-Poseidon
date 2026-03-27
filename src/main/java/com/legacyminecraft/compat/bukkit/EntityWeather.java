package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat weather entity alias.
 */
public class EntityWeather extends Entity implements Weather {
    public EntityWeather() {
    }

    public EntityWeather(WorldServer world) {
        this.world = world;
    }
}
