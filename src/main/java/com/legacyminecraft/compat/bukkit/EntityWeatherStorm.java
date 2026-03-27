package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat weather-storm entity scaffold.
 */
public class EntityWeatherStorm extends EntityWeather {
    public boolean isEffect;

    public EntityWeatherStorm() {
    }

    public EntityWeatherStorm(WorldServer world, double x, double y, double z, boolean isEffect) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
        this.isEffect = isEffect;
    }

    public EntityWeatherStorm(WorldServer world, double x, double y, double z) {
        this(world, x, y, z, false);
    }
}
