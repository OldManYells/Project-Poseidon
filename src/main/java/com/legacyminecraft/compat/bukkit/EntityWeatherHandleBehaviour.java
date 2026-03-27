package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for resolving CraftWeather typed handles.
 */
public final class EntityWeatherHandleBehaviour {
    private static final EntityWeatherHandleBehaviour INSTANCE = new EntityWeatherHandleBehaviour();

    private EntityWeatherHandleBehaviour() {
    }

    public static EntityWeatherHandleBehaviour getInstance() {
        return INSTANCE;
    }

    public EntityWeather resolveHandle(Entity entity) {
        return (EntityWeather) entity;
    }
}
