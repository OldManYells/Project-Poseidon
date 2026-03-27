package com.legacyminecraft.compat.bukkit;


import java.io.File;

/**
 * Canonical behaviour for CraftServer world-instance creation wrapper glue.
 */
public final class CraftServerWorldInstanceCreationBehaviour {
    private static final CraftServerWorldInstanceCreationBehaviour INSTANCE =
            new CraftServerWorldInstanceCreationBehaviour();

    private CraftServerWorldInstanceCreationBehaviour() {
    }

    public static CraftServerWorldInstanceCreationBehaviour getInstance() {
        return INSTANCE;
    }

    public WorldServer createWorldServer(MinecraftServer console, String name, long seed, Environment environment, ChunkGenerator generator) {
        int dimension = 10 + console.worlds.size();
        return new WorldServer(console, new ServerNBTManager(new File("."), name, true), name, dimension, seed, environment, generator);
    }
}
