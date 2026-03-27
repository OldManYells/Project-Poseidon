package com.legacyminecraft.compat.bukkit;


import java.util.List;

/**
 * Canonical behaviour for CraftWorld metadata/accessor wrapper glue.
 */
public final class CraftWorldMetadataAccessBehaviour {
    private static final CraftWorldMetadataAccessBehaviour INSTANCE = new CraftWorldMetadataAccessBehaviour();

    private CraftWorldMetadataAccessBehaviour() {
    }

    public static CraftWorldMetadataAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public World.Environment getEnvironment(World.Environment environment) {
        return environment;
    }

    public ChunkGenerator getGenerator(ChunkGenerator generator) {
        return generator;
    }

    public List<BlockPopulator> getPopulators(List<BlockPopulator> populators) {
        return populators;
    }
}
