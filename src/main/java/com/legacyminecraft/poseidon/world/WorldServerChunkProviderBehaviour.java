package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

/**
 * Canonical behaviour for world-server chunk provider selection and construction.
 */
public final class WorldServerChunkProviderBehaviour {
    private static final WorldServerChunkProviderBehaviour INSTANCE = new WorldServerChunkProviderBehaviour();

    private WorldServerChunkProviderBehaviour() {
    }

    public static WorldServerChunkProviderBehaviour getInstance() {
        return INSTANCE;
    }

    public Object createChunkProvider(
            Object world,
            Object chunkLoader,
            Object worldProvider,
            Object generator,
            long seed
    ) {
        Object chunkGenerator = this.selectChunkGenerator(world, worldProvider, generator, seed);
        return LegacyCompatGatewayRegistry.gateway().createChunkProviderServer(world, chunkLoader, chunkGenerator);
    }

    public Object selectChunkGenerator(
            Object world,
            Object worldProvider,
            Object generator,
            long seed
    ) {
        String providerName = worldProvider == null ? "" : worldProvider.getClass().getSimpleName();

        if ("WorldProviderHell".equals(providerName)) {
            return LegacyCompatGatewayRegistry.gateway().createChunkProviderByWorldProvider(world, worldProvider, seed);
        }

        if ("WorldProviderSky".equals(providerName)) {
            return LegacyCompatGatewayRegistry.gateway().createChunkProviderByWorldProvider(world, worldProvider, seed);
        }

        return LegacyCompatGatewayRegistry.gateway().createChunkProviderByWorldProvider(world, worldProvider, seed);
    }
}
