package net.minecraft.server;

import com.legacyminecraft.poseidon.world.biome.WorldChunkClimateBehaviour;

public class WorldChunkManager {

    private NoiseGeneratorOctaves2 e;
    private NoiseGeneratorOctaves2 f;
    private NoiseGeneratorOctaves2 g;
    public double[] temperature;
    public double[] rain;
    public double[] c;
    public BiomeBase[] d;
    private static final WorldChunkClimateBehaviour WORLD_CHUNK_CLIMATE_BEHAVIOUR = WorldChunkClimateBehaviour.getInstance();

    protected WorldChunkManager() {}

    public WorldChunkManager(World world) {
        this.e = WORLD_CHUNK_CLIMATE_BEHAVIOUR.createTemperatureNoise(world.getSeed());
        this.f = WORLD_CHUNK_CLIMATE_BEHAVIOUR.createHumidityNoise(world.getSeed());
        this.g = WORLD_CHUNK_CLIMATE_BEHAVIOUR.createBlendNoise(world.getSeed());
    }

    public BiomeBase a(ChunkCoordIntPair chunkcoordintpair) {
        return this.getBiome(chunkcoordintpair.x << 4, chunkcoordintpair.z << 4);
    }

    public BiomeBase getBiome(int i, int j) {
        return this.getBiomeData(i, j, 1, 1)[0];
    }

    public BiomeBase[] getBiomeData(int i, int j, int k, int l) {
        this.d = this.a(this.d, i, j, k, l);
        return this.d;
    }

    public double[] a(double[] adouble, int i, int j, int k, int l) {
        WorldChunkClimateBehaviour.TemperatureState temperatureState = WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleTemperatureMap(
                adouble,
                this.c,
                this.e,
                this.g,
                i,
                j,
                k,
                l
        );
        this.c = temperatureState.getBlend();
        return temperatureState.getTemperatures();
    }

    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        WorldChunkClimateBehaviour.BiomeClimateState biomeClimateState = WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleBiomeClimate(
                abiomebase,
                this.temperature,
                this.rain,
                this.c,
                this.e,
                this.f,
                this.g,
                i,
                j,
                k,
                l
        );
        this.temperature = biomeClimateState.getTemperatures();
        this.rain = biomeClimateState.getRain();
        this.c = biomeClimateState.getBlend();
        return biomeClimateState.getBiomes();
    }

    // CraftBukkit start
    public double getHumidity(int x, int z) {
        return WORLD_CHUNK_CLIMATE_BEHAVIOUR.sampleHumidity(this.f, this.rain, x, z);
    }
    // CraftBukkit end
}
