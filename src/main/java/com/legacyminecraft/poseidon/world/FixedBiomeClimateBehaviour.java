package com.legacyminecraft.poseidon.world;


import java.util.Arrays;

public final class FixedBiomeClimateBehaviour {
    private static final FixedBiomeClimateBehaviour INSTANCE = new FixedBiomeClimateBehaviour();

    private FixedBiomeClimateBehaviour() {
    }

    public static FixedBiomeClimateBehaviour getInstance() {
        return INSTANCE;
    }

    public BiomeBase biomeAt(BiomeBase biome) {
        return biome;
    }

    public BiomeBase[] fillBiomeArray(BiomeBase[] abiomebase, int k, int l, BiomeBase biome) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        Arrays.fill(abiomebase, 0, k * l, biome);
        return abiomebase;
    }

    public double[] fillTemperatureArray(double[] values, int k, int l, double constantTemperature) {
        if (values == null || values.length < k * l) {
            values = new double[k * l];
        }

        Arrays.fill(values, 0, k * l, constantTemperature);
        return values;
    }

    public ClimateArrays fillClimate(BiomeBase[] biomes, double[] rain, double[] temperature, int k, int l, BiomeBase biome, double rainValue, double temperatureValue) {
        biomes = this.fillBiomeArray(biomes, k, l, biome);

        if (temperature == null || temperature.length < k * l) {
            temperature = new double[k * l];
            rain = new double[k * l];
        }

        Arrays.fill(rain, 0, k * l, rainValue);
        Arrays.fill(temperature, 0, k * l, temperatureValue);
        return new ClimateArrays(biomes, rain, temperature);
    }

    public static final class ClimateArrays {
        public final BiomeBase[] biomes;
        public final double[] rain;
        public final double[] temperature;

        ClimateArrays(BiomeBase[] biomes, double[] rain, double[] temperature) {
            this.biomes = biomes;
            this.rain = rain;
            this.temperature = temperature;
        }
    }
}
