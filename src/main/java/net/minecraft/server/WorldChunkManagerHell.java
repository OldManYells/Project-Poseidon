package net.minecraft.server;

import com.legacyminecraft.poseidon.world.FixedBiomeClimateBehaviour;

public class WorldChunkManagerHell extends WorldChunkManager {
    private static final FixedBiomeClimateBehaviour FIXED_BIOME_CLIMATE_BEHAVIOUR = FixedBiomeClimateBehaviour.getInstance();

    private BiomeBase e;
    private double f;
    private double g;

    public WorldChunkManagerHell(BiomeBase biomebase, double d0, double d1) {
        this.e = biomebase;
        this.f = d0;
        this.g = d1;
    }

    public BiomeBase a(ChunkCoordIntPair chunkcoordintpair) {
        return FIXED_BIOME_CLIMATE_BEHAVIOUR.biomeAt(this.e);
    }

    public BiomeBase getBiome(int i, int j) {
        return FIXED_BIOME_CLIMATE_BEHAVIOUR.biomeAt(this.e);
    }

    public BiomeBase[] getBiomeData(int i, int j, int k, int l) {
        this.d = this.a(this.d, i, j, k, l);
        return this.d;
    }

    public double[] a(double[] adouble, int i, int j, int k, int l) {
        return FIXED_BIOME_CLIMATE_BEHAVIOUR.fillTemperatureArray(adouble, k, l, this.f);
    }

    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        FixedBiomeClimateBehaviour.ClimateArrays climate = FIXED_BIOME_CLIMATE_BEHAVIOUR.fillClimate(abiomebase, this.rain, this.temperature, k, l, this.e, this.g, this.f);
        this.rain = climate.rain;
        this.temperature = climate.temperature;
        return climate.biomes;
    }
}
