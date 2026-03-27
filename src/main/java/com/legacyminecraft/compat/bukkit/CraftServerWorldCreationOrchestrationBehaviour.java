package com.legacyminecraft.compat.bukkit;


import java.io.File;

/**
 * Canonical behaviour for CraftServer create-world orchestration wrapper glue.
 */
public final class CraftServerWorldCreationOrchestrationBehaviour {
    public interface WorldCreationActions {
        World resolveExistingWorldOrThrow(World existingWorld, File folder, String name);

        ChunkGenerator resolveGenerator(ChunkGenerator generator, String name);

        void convertIfNeeded(File folder, String name);

        WorldServer createWorldServer(String name, long seed, Environment environment, ChunkGenerator generator);

        World finalizeWorldCreation(WorldServer internal, String name, ChunkGenerator generator);
    }

    private static final CraftServerWorldCreationOrchestrationBehaviour INSTANCE =
            new CraftServerWorldCreationOrchestrationBehaviour();

    private CraftServerWorldCreationOrchestrationBehaviour() {
    }

    public static CraftServerWorldCreationOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator, WorldCreationActions actions, World existingWorld) {
        File folder = new File(name);

        World world = actions.resolveExistingWorldOrThrow(existingWorld, folder, name);
        if (world != null) {
            return world;
        }

        ChunkGenerator resolvedGenerator = actions.resolveGenerator(generator, name);
        actions.convertIfNeeded(folder, name);
        WorldServer internal = actions.createWorldServer(name, seed, environment, resolvedGenerator);
        return actions.finalizeWorldCreation(internal, name, resolvedGenerator);
    }
}
