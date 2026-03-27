package com.legacyminecraft.poseidon.world;

/**
 * World-local hell chunk manager scaffold.
 */
public class WorldChunkManagerHell extends WorldChunkManager {
    private final BiomeBase biome;
    private final double temperature;
    private final double humidity;

    public WorldChunkManagerHell(BiomeBase biome, double temperature, double humidity) {
        this.biome = biome;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public BiomeBase getBiome() {
        return biome;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
