package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.IChunkProvider;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderHell;
import net.minecraft.server.WorldProviderSky;
import net.minecraft.server.WorldServer;
import org.bukkit.craftbukkit.generator.CustomChunkGenerator;
import org.bukkit.craftbukkit.generator.NetherChunkGenerator;
import org.bukkit.craftbukkit.generator.NormalChunkGenerator;
import org.bukkit.craftbukkit.generator.SkyLandsChunkGenerator;
import org.bukkit.generator.ChunkGenerator;

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
}
