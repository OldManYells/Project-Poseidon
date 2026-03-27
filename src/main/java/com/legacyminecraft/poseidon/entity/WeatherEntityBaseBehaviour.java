package com.legacyminecraft.poseidon.entity;


/**
 * Canonical base hook for weather-entity wrappers.
 */
public final class WeatherEntityBaseBehaviour {
    private static final WeatherEntityBaseBehaviour INSTANCE = new WeatherEntityBaseBehaviour();

    private WeatherEntityBaseBehaviour() {
    }

    public static WeatherEntityBaseBehaviour getInstance() {
        return INSTANCE;
    }

    public void initialize(EntityWeather weatherEntity, World world) {
        // Base weather entity currently uses vanilla Entity constructor state only.
    }
}
