package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftServer world-generator fallback wrapper glue.
 */
public final class CraftServerWorldGeneratorResolutionBehaviour {
    public interface GeneratorLookup {
        ChunkGenerator getGenerator(String worldName);
    }

    private static final CraftServerWorldGeneratorResolutionBehaviour INSTANCE =
            new CraftServerWorldGeneratorResolutionBehaviour();

    private CraftServerWorldGeneratorResolutionBehaviour() {
    }

    public static CraftServerWorldGeneratorResolutionBehaviour getInstance() {
        return INSTANCE;
    }

    public ChunkGenerator resolve(ChunkGenerator generator, String worldName, GeneratorLookup lookup) {
        if (generator == null) {
            return lookup.getGenerator(worldName);
        }
        return generator;
    }
}
