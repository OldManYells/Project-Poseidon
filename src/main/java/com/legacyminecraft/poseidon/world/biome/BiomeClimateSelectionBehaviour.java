package com.legacyminecraft.poseidon.world.biome;

import net.minecraft.server.BiomeBase;

/**
 * Canonical climate-to-biome lookup policy and bootstrap service.
 */
public final class BiomeClimateSelectionBehaviour {
    private static final BiomeClimateSelectionBehaviour INSTANCE = new BiomeClimateSelectionBehaviour();

    private BiomeClimateSelectionBehaviour() {
    }

    public static BiomeClimateSelectionBehaviour getInstance() {
        return INSTANCE;
    }

    public void bootstrapClimateLookupTable(BiomeBase[] lookupTable, BiomeBase desert, BiomeBase iceDesert, byte sandBlockId) {
        populateLookupTable(lookupTable);
        applyDesertSurfaceOverrides(desert, iceDesert, sandBlockId);
    }

    public void populateLookupTable(BiomeBase[] lookupTable) {
        for (int temperatureIndex = 0; temperatureIndex < 64; ++temperatureIndex) {
            for (int humidityIndex = 0; humidityIndex < 64; ++humidityIndex) {
                lookupTable[temperatureIndex + humidityIndex * 64] = selectClimateBiome(
                        (float) temperatureIndex / 63.0F,
                        (float) humidityIndex / 63.0F
                );
            }
        }
    }

    public void applyDesertSurfaceOverrides(BiomeBase desert, BiomeBase iceDesert, byte sandBlockId) {
        desert.p = desert.q = sandBlockId;
        iceDesert.p = iceDesert.q = sandBlockId;
    }

    public BiomeBase lookupBiome(BiomeBase[] lookupTable, double temperature, double humidity) {
        int temperatureIndex = (int) (temperature * 63.0D);
        int humidityIndex = (int) (humidity * 63.0D);
        return lookupTable[temperatureIndex + humidityIndex * 64];
    }

    public BiomeBase selectClimateBiome(float temperature, float humidity) {
        humidity *= temperature;
        return temperature < 0.1F
                ? BiomeBase.TUNDRA
                : (humidity < 0.2F
                ? (temperature < 0.5F ? BiomeBase.TUNDRA : (temperature < 0.95F ? BiomeBase.SAVANNA : BiomeBase.DESERT))
                : (humidity > 0.5F && temperature < 0.7F
                ? BiomeBase.SWAMPLAND
                : (temperature < 0.5F
                ? BiomeBase.TAIGA
                : (temperature < 0.97F
                ? (humidity < 0.35F ? BiomeBase.SHRUBLAND : BiomeBase.FOREST)
                : (humidity < 0.45F ? BiomeBase.PLAINS : (humidity < 0.9F ? BiomeBase.SEASONAL_FOREST : BiomeBase.RAINFOREST))))));
    }
}
