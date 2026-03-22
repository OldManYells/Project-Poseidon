package com.legacyminecraft.poseidon.world;

import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IDataManager;
import net.minecraft.server.IProgressUpdate;
import net.minecraft.server.World;
import net.minecraft.server.WorldData;
import net.minecraft.server.WorldMapCollection;

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

    public void persistWorldState(World world, IDataManager dataManager, WorldData worldData, List players, WorldMapCollection worldMaps) {
        world.k();
        dataManager.a(worldData, players);
        worldMaps.a();
    }

    public void saveChunks(IChunkProvider chunkProvider, boolean forceSave, IProgressUpdate progressUpdate) {
        chunkProvider.saveChunks(forceSave, progressUpdate);
    }
}
