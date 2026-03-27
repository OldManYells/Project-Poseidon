package com.legacyminecraft.poseidon.world;


import java.io.File;
import java.util.List;
import java.util.UUID;

public interface DataManagerContract {
    WorldData loadWorldData();

    void verifySessionLock();

    IChunkLoader getChunkLoader(WorldProvider worldProvider);

    void saveWorldDataWithPlayers(WorldData worldData, List playerData);

    void saveWorldData(WorldData worldData);

    PlayerFileData getPlayerFileData();

    void flush();

    File getDataFile(String path);

    UUID getWorldUuid();
}
