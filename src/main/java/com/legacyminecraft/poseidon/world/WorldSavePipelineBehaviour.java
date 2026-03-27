package com.legacyminecraft.poseidon.world;


import java.util.List;

/**
 * Canonical behaviour for world save pipeline orchestration.
 */
public final class WorldSavePipelineBehaviour {
    private static final WorldSavePipelineBehaviour INSTANCE = new WorldSavePipelineBehaviour();

    private WorldSavePipelineBehaviour() {
    }

    public static WorldSavePipelineBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canSave(IChunkProvider chunkProvider) {
        return chunkProvider.canSave();
    }

    public void notifySavingLevel(IProgressUpdate progressUpdate) {
        if (progressUpdate != null) {
            progressUpdate.a("Saving level");
        }
    }

    public void notifySavingChunks(IProgressUpdate progressUpdate) {
        if (progressUpdate != null) {
            progressUpdate.b("Saving chunks");
        }
    }

    public void persistWorldState(World world, IDataManager dataManager, WorldData worldData, List players, Object worldMaps) {
        world.k();
        dataManager.a(worldData, players);
        if (worldMaps != null) {
            try {
                worldMaps.getClass().getMethod("a").invoke(worldMaps);
            } catch (ReflectiveOperationException ignored) {
            }
        }
    }

    public void saveChunks(IChunkProvider chunkProvider, boolean forceSave, IProgressUpdate progressUpdate) {
        chunkProvider.saveChunks(forceSave, progressUpdate);
    }
}
