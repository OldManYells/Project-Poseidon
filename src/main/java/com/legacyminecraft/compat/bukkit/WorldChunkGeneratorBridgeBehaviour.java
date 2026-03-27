package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for selecting CraftBukkit-backed chunk generators.
 */
public final class WorldChunkGeneratorBridgeBehaviour {
    private static final WorldChunkGeneratorBridgeBehaviour INSTANCE = new WorldChunkGeneratorBridgeBehaviour();

    private WorldChunkGeneratorBridgeBehaviour() {
    }

    public static WorldChunkGeneratorBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public IChunkProvider selectChunkGenerator(
            WorldServer world,
            WorldProvider worldProvider,
            ChunkGenerator generator,
            long seed
    ) {
        if (generator != null) {
            return new CustomChunkGenerator(world, seed, generator);
        }

        if (worldProvider instanceof WorldProviderHell) {
            return new NetherChunkGenerator(world, seed);
        }

        if (worldProvider instanceof WorldProviderSky) {
            return new SkyLandsChunkGenerator(world, seed);
        }

        return new NormalChunkGenerator(world, seed);
    }

    public Object selectChunkGenerator(
            Object world,
            Object worldProvider,
            Object generator,
            long seed
    ) {
        return selectChunkGenerator(
                (WorldServer) world,
                (WorldProvider) worldProvider,
                (ChunkGenerator) generator,
                seed
        );
    }
}
