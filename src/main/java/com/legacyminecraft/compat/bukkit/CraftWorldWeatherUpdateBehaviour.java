package com.legacyminecraft.compat.bukkit;


import java.util.Random;

/**
 * Canonical behavior for CraftWorld weather/thunder update event flow.
 */
public final class CraftWorldWeatherUpdateBehaviour {
    private static final CraftWorldWeatherUpdateBehaviour INSTANCE = new CraftWorldWeatherUpdateBehaviour();

    private CraftWorldWeatherUpdateBehaviour() {
    }

    public static CraftWorldWeatherUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public void setStorm(World world, Server server, WorldServer worldServer, boolean hasStorm, Random random) {
        WeatherChangeEvent weatherChangeEvent = new WeatherChangeEvent(world, hasStorm);
        server.getPluginManager().callEvent(weatherChangeEvent);
        if (weatherChangeEvent.isCancelled()) {
            return;
        }

        worldServer.worldData.setStorm(hasStorm);
        if (hasStorm) {
            worldServer.worldData.setWeatherDuration(random.nextInt(12000) + 12000);
        } else {
            worldServer.worldData.setWeatherDuration(random.nextInt(168000) + 12000);
        }
    }

    public void setThundering(World world, Server server, WorldServer worldServer, boolean thundering, Random random) {
        ThunderChangeEvent thunderChangeEvent = new ThunderChangeEvent(world, thundering);
        server.getPluginManager().callEvent(thunderChangeEvent);
        if (thunderChangeEvent.isCancelled()) {
            return;
        }

        worldServer.worldData.setThundering(thundering);
        if (thundering) {
            worldServer.worldData.setThunderDuration(random.nextInt(12000) + 3600);
        } else {
            worldServer.worldData.setThunderDuration(random.nextInt(168000) + 12000);
        }
    }
}
