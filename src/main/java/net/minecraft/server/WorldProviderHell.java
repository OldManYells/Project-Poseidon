package net.minecraft.server;

import com.legacyminecraft.poseidon.world.HellWorldProviderBehaviour;
import com.legacyminecraft.poseidon.world.WorldProviderBehaviour;

public class WorldProviderHell extends WorldProvider {
    private static final HellWorldProviderBehaviour HELL_WORLD_PROVIDER_BEHAVIOUR = HellWorldProviderBehaviour.getInstance();
    private static final WorldProviderBehaviour WORLD_PROVIDER_BEHAVIOUR = WorldProviderBehaviour.getInstance();

    public WorldProviderHell() {}

    public void a() {
        HellWorldProviderBehaviour.HellConfiguration config = HELL_WORLD_PROVIDER_BEHAVIOUR.configure(this.a);
        this.b = config.chunkManager;
        this.c = config.c;
        this.d = config.d;
        this.e = config.e;
        this.dimension = config.dimension;
    }

    protected void c() {
        this.f = WORLD_PROVIDER_BEHAVIOUR.buildLightBrightnessTable(0.1F);
    }

    public IChunkProvider getChunkProvider() {
        return HELL_WORLD_PROVIDER_BEHAVIOUR.createChunkProvider(this.a);
    }

    public boolean canSpawn(int i, int j) {
        return HELL_WORLD_PROVIDER_BEHAVIOUR.canSpawn(this.a, i, j);
    }

    public float a(long i, float f) {
        return HELL_WORLD_PROVIDER_BEHAVIOUR.celestialAngle(i, f);
    }

    public boolean d() {
        return HELL_WORLD_PROVIDER_BEHAVIOUR.hasSkyLight();
    }
}
