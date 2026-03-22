package net.minecraft.server;

import com.legacyminecraft.poseidon.world.WorldProviderBehaviour;

public abstract class WorldProvider {
    private static final WorldProviderBehaviour WORLD_PROVIDER_BEHAVIOUR = WorldProviderBehaviour.getInstance();

    public World a;
    public WorldChunkManager b;
    public boolean c = false;
    public boolean d = false;
    public boolean e = false;
    public float[] f = new float[16];
    public int dimension = 0;
    private float[] h = new float[4];

    public WorldProvider() {}

    public final void a(World world) {
        this.a = world;
        this.a();
        this.c();
    }

    protected void c() {
        this.f = WORLD_PROVIDER_BEHAVIOUR.buildLightBrightnessTable(0.05F);
    }

    protected void a() {
        this.b = WORLD_PROVIDER_BEHAVIOUR.createDefaultChunkManager(this.a);
    }

    public IChunkProvider getChunkProvider() {
        return WORLD_PROVIDER_BEHAVIOUR.createOverworldChunkProvider(this.a);
    }

    public boolean canSpawn(int i, int j) {
        return WORLD_PROVIDER_BEHAVIOUR.canSpawnOnSand(this.a, i, j);
    }

    public float a(long i, float f) {
        return WORLD_PROVIDER_BEHAVIOUR.computeCelestialAngle(i, f);
    }

    public boolean d() {
        return WORLD_PROVIDER_BEHAVIOUR.hasSkyLight();
    }

    public static WorldProvider byDimension(int i) {
        return WORLD_PROVIDER_BEHAVIOUR.byDimension(i);
    }
}
