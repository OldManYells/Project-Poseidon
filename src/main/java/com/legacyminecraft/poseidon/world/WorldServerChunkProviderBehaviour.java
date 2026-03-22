package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.compat.bukkit.WorldChunkGeneratorBridgeBehaviour;
import net.minecraft.server.ChunkProviderServer;
import net.minecraft.server.IChunkLoader;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldServer;
import org.bukkit.generator.ChunkGenerator;

/**
 * Canonical behaviour for world-server chunk provider selection and construction.
 */
public final class WorldServerChunkProviderBehaviour {
    private static final WorldServerChunkProviderBehaviour INSTANCE = new WorldServerChunkProviderBehaviour();
    private static final WorldChunkGeneratorBridgeBehaviour WORLD_CHUNK_GENERATOR_BRIDGE = WorldChunkGeneratorBridgeBehaviour.getInstance();

    private WorldServerChunkProviderBehaviour() {
    }

    public static WorldServerChunkProviderBehaviour getInstance() {
        return INSTANCE;
    }

    public ChunkProviderServer createChunkProvider(
            WorldServer world,
            IChunkLoader chunkLoader,
            WorldProvider worldProvider,
            ChunkGenerator generator,
            long seed
    ) {
        IChunkProvider chunkGenerator = this.selectChunkGenerator(world, worldProvider, generator, seed);
        return new ChunkProviderServer(world, chunkLoader, chunkGenerator);
    }

    public IChunkProvider selectChunkGenerator(
            WorldServer world,
            WorldProvider worldProvider,
            ChunkGenerator generator,
            long seed
    ) {
        return WORLD_CHUNK_GENERATOR_BRIDGE.selectChunkGenerator(world, worldProvider, generator, seed);
    }
}
