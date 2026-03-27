package net.minecraft.server;

public class WorldChunkManager {

    private NoiseGeneratorOctaves2 e;
    private NoiseGeneratorOctaves2 f;
    private NoiseGeneratorOctaves2 g;
    public double[] temperature;
    public double[] rain;
    public double[] c;
    public BiomeBase[] d;
    protected WorldChunkManager() {}

    public WorldChunkManager(World world) {
        this.e = new NoiseGeneratorOctaves2(new java.util.Random(world.getSeed() * 9871L), 4);
        this.f = new NoiseGeneratorOctaves2(new java.util.Random(world.getSeed() * 39811L), 4);
        this.g = new NoiseGeneratorOctaves2(new java.util.Random(world.getSeed() * 543321L), 2);
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
        if (adouble == null || adouble.length < k * l) {
            adouble = new double[k * l];
        }
        this.c = this.g.a(this.c, (double) i, (double) j, k, l, 0.25D, 0.25D, 0.5882352941176471D);
        return this.e.a(adouble, (double) i, (double) j, k, l, 0.025D, 0.025D, 0.25D);
    }

    public BiomeBase[] a(BiomeBase[] abiomebase, int i, int j, int k, int l) {
        if (abiomebase == null || abiomebase.length < k * l) {
            abiomebase = new BiomeBase[k * l];
        }

        this.temperature = this.e.a(this.temperature, (double) i, (double) j, k, l, 0.025D, 0.025D, 0.25D);
        this.rain = this.f.a(this.rain, (double) i, (double) j, k, l, 0.05D, 0.05D, 0.3333333333333333D);
        this.c = this.g.a(this.c, (double) i, (double) j, k, l, 0.25D, 0.25D, 0.5882352941176471D);

        int index = 0;
        for (int x = 0; x < k; ++x) {
            for (int z = 0; z < l; ++z) {
                double blend = this.c[index] * 1.1D + 0.5D;
                double temperatureSample = (this.temperature[index] * 0.15D + 0.7D) * 0.99D + blend * 0.01D;
                double rainSample = (this.rain[index] * 0.15D + 0.5D) * 0.998D + blend * 0.002D;
                if (temperatureSample < 0.0D) {
                    temperatureSample = 0.0D;
                }
                if (rainSample < 0.0D) {
                    rainSample = 0.0D;
                }
                if (temperatureSample > 1.0D) {
                    temperatureSample = 1.0D;
                }
                if (rainSample > 1.0D) {
                    rainSample = 1.0D;
                }
                this.temperature[index] = temperatureSample;
                this.rain[index] = rainSample;
                abiomebase[index] = BiomeBase.a(temperatureSample, rainSample);
                ++index;
            }
        }
        return abiomebase;
    }

    // CraftBukkit start
    public double getHumidity(int x, int z) {
        if (this.rain == null || this.rain.length < 1) {
            this.rain = new double[1];
        }
        this.rain = this.f.a(this.rain, (double) x, (double) z, 1, 1, 0.05D, 0.05D, 0.3333333333333333D);
        return this.rain[0];
    }
    // CraftBukkit end
}
