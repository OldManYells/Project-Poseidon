package com.legacyminecraft.poseidon.world.biome;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.NoiseGeneratorOctaves2;

import java.util.Random;

/**
 * Canonical dynamic world-climate sampling and biome assignment behaviour.
 */
public final class WorldChunkClimateBehaviour {
    private static final WorldChunkClimateBehaviour INSTANCE = new WorldChunkClimateBehaviour();

    private static final double TEMPERATURE_SCALE = 0.02500000037252903D;
    private static final double HUMIDITY_SCALE = 0.05000000074505806D;
    private static final double BLEND_SCALE = 0.25D;
    private static final double TEMPERATURE_WEIGHT = 0.01D;
    private static final double HUMIDITY_WEIGHT = 0.0020D;

    private WorldChunkClimateBehaviour() {
    }

    public static WorldChunkClimateBehaviour getInstance() {
        return INSTANCE;
    }

    public NoiseGeneratorOctaves2 createTemperatureNoise(long seed) {
        return new NoiseGeneratorOctaves2(new Random(seed * 9871L), 4);
    }

    public NoiseGeneratorOctaves2 createHumidityNoise(long seed) {
        return new NoiseGeneratorOctaves2(new Random(seed * 39811L), 4);
    }

    public NoiseGeneratorOctaves2 createBlendNoise(long seed) {
        return new NoiseGeneratorOctaves2(new Random(seed * 543321L), 2);
    }

    public TemperatureState sampleTemperatureMap(
            double[] temperatures,
            double[] blend,
            NoiseGeneratorOctaves2 temperatureNoise,
            NoiseGeneratorOctaves2 blendNoise,
            int originX,
            int originZ,
            int width,
            int height
    ) {
        int sampleCount = width * height;
        if (temperatures == null || temperatures.length < sampleCount) {
            temperatures = new double[sampleCount];
        }

        temperatures = temperatureNoise.a(
                temperatures,
                (double) originX,
                (double) originZ,
                width,
                height,
                TEMPERATURE_SCALE,
                TEMPERATURE_SCALE,
                0.25D
        );
        blend = blendNoise.a(
                blend,
                (double) originX,
                (double) originZ,
                width,
                height,
                BLEND_SCALE,
                BLEND_SCALE,
                0.5882352941176471D
        );

        int index = 0;
        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < height; ++z) {
                double blendFactor = blend[index] * 1.1D + 0.5D;
                double temperature = blendClimateSample(temperatures[index], blendFactor, TEMPERATURE_WEIGHT, 0.7D);
                temperatures[index] = clampUnit(curveTemperature(temperature));
                ++index;
            }
        }

        return new TemperatureState(temperatures, blend);
    }

    public BiomeClimateState sampleBiomeClimate(
            BiomeBase[] biomes,
            double[] temperatures,
            double[] rain,
            double[] blend,
            NoiseGeneratorOctaves2 temperatureNoise,
            NoiseGeneratorOctaves2 humidityNoise,
            NoiseGeneratorOctaves2 blendNoise,
            int originX,
            int originZ,
            int width,
            int height
    ) {
        int sampleCount = width * height;
        if (biomes == null || biomes.length < sampleCount) {
            biomes = new BiomeBase[sampleCount];
        }

        temperatures = temperatureNoise.a(
                temperatures,
                (double) originX,
                (double) originZ,
                width,
                width,
                TEMPERATURE_SCALE,
                TEMPERATURE_SCALE,
                0.25D
        );
        rain = humidityNoise.a(
                rain,
                (double) originX,
                (double) originZ,
                width,
                width,
                HUMIDITY_SCALE,
                HUMIDITY_SCALE,
                0.3333333333333333D
        );
        blend = blendNoise.a(
                blend,
                (double) originX,
                (double) originZ,
                width,
                width,
                BLEND_SCALE,
                BLEND_SCALE,
                0.5882352941176471D
        );

        int index = 0;
        for (int x = 0; x < width; ++x) {
            for (int z = 0; z < height; ++z) {
                double blendFactor = blend[index] * 1.1D + 0.5D;
                double temperature = blendClimateSample(temperatures[index], blendFactor, TEMPERATURE_WEIGHT, 0.7D);
                double humidity = blendClimateSample(rain[index], blendFactor, HUMIDITY_WEIGHT, 0.5D);

                temperature = clampUnit(curveTemperature(temperature));
                humidity = clampUnit(humidity);

                temperatures[index] = temperature;
                rain[index] = humidity;
                biomes[index] = BiomeBase.a(temperature, humidity);
                ++index;
            }
        }

        return new BiomeClimateState(biomes, temperatures, rain, blend);
    }

    public double sampleHumidity(NoiseGeneratorOctaves2 humidityNoise, double[] rain, int x, int z) {
        return humidityNoise.a(
                rain,
                (double) x,
                (double) z,
                1,
                1,
                HUMIDITY_SCALE,
                HUMIDITY_SCALE,
                0.3333333333333333D
        )[0];
    }

    private double blendClimateSample(double source, double blendFactor, double blendWeight, double baseOffset) {
        double sourceWeight = 1.0D - blendWeight;
        return (source * 0.15D + baseOffset) * sourceWeight + blendFactor * blendWeight;
    }

    private double curveTemperature(double temperature) {
        return 1.0D - (1.0D - temperature) * (1.0D - temperature);
    }

    private double clampUnit(double value) {
        if (value < 0.0D) {
            return 0.0D;
        }
        if (value > 1.0D) {
            return 1.0D;
        }
        return value;
    }

    public static final class TemperatureState {
        private final double[] temperatures;
        private final double[] blend;

        private TemperatureState(double[] temperatures, double[] blend) {
            this.temperatures = temperatures;
            this.blend = blend;
        }

        public double[] getTemperatures() {
            return temperatures;
        }

        public double[] getBlend() {
            return blend;
        }
    }

    public static final class BiomeClimateState {
        private final BiomeBase[] biomes;
        private final double[] temperatures;
        private final double[] rain;
        private final double[] blend;

        private BiomeClimateState(BiomeBase[] biomes, double[] temperatures, double[] rain, double[] blend) {
            this.biomes = biomes;
            this.temperatures = temperatures;
            this.rain = rain;
            this.blend = blend;
        }

        public BiomeBase[] getBiomes() {
            return biomes;
        }

        public double[] getTemperatures() {
            return temperatures;
        }

        public double[] getRain() {
            return rain;
        }

        public double[] getBlend() {
            return blend;
        }
    }
}
