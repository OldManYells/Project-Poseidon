package com.legacyminecraft.poseidon.world;


public final class HellWorldProviderBehaviour {
    private static final HellWorldProviderBehaviour INSTANCE = new HellWorldProviderBehaviour();

    private HellWorldProviderBehaviour() {
    }

    public static HellWorldProviderBehaviour getInstance() {
        return INSTANCE;
    }

    public HellConfiguration configure(World world) {
        return new HellConfiguration(new WorldChunkManagerHell(BiomeBase.HELL, 1.0D, 0.0D), true, true, true, -1);
    }

    public IChunkProvider createChunkProvider(World world) {
        return new ChunkProviderHell(world, world.getSeed());
    }

    public boolean canSpawn(World world, int i, int j) {
        int k = world.a(i, j);
        return k != Block.BEDROCK.id && k != 0 && Block.o[k];
    }

    public float celestialAngle(long i, float f) {
        return 0.5F;
    }

    public boolean hasSkyLight() {
        return false;
    }

    public static final class HellConfiguration {
        public final WorldChunkManagerHell chunkManager;
        public final boolean c;
        public final boolean d;
        public final boolean e;
        public final int dimension;

        HellConfiguration(WorldChunkManagerHell chunkManager, boolean c, boolean d, boolean e, int dimension) {
            this.chunkManager = chunkManager;
            this.c = c;
            this.d = d;
            this.e = e;
            this.dimension = dimension;
        }
    }
}
