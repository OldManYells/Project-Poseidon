package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat weather-change event scaffold.
 */
public class WeatherChangeEvent {
    private final World world;
    private final boolean toWeatherState;
    private boolean cancelled;

    public WeatherChangeEvent(World world, boolean toWeatherState) {
        this.world = world;
        this.toWeatherState = toWeatherState;
    }

    public World getWorld() {
        return world;
    }

    public boolean toWeatherState() {
        return toWeatherState;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
