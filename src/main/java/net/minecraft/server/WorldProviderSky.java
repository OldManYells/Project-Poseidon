package net.minecraft.server;

import com.legacyminecraft.poseidon.world.SkyWorldProviderBehaviour;

public class WorldProviderSky extends WorldProvider {
    private static final SkyWorldProviderBehaviour SKY_WORLD_PROVIDER_BEHAVIOUR = SkyWorldProviderBehaviour.getInstance();

    public WorldProviderSky() {}

    public void a() {
        SkyWorldProviderBehaviour.SkyConfiguration config = SKY_WORLD_PROVIDER_BEHAVIOUR.configure(this.a);
        this.b = config.chunkManager;
        this.dimension = config.dimension;
    }

    public IChunkProvider getChunkProvider() {
        return SKY_WORLD_PROVIDER_BEHAVIOUR.createChunkProvider(this.a);
    }

    public float a(long i, float f) {
        return SKY_WORLD_PROVIDER_BEHAVIOUR.celestialAngle(i, f);
    }

    public boolean canSpawn(int i, int j) {
        return SKY_WORLD_PROVIDER_BEHAVIOUR.canSpawn(this.a, i, j);
    }
}
