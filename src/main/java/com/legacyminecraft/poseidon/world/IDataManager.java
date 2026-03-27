package com.legacyminecraft.poseidon.world;

import java.io.File;
import java.util.UUID;

/**
 * World-local data manager contract.
 */
public interface IDataManager {
    WorldData getWorldData();

    void checkSession();

    IChunkLoader createChunkLoader(WorldProvider worldProvider);

    void saveWorldDataWithPlayer(WorldData worldData, NBTTagCompound playerData);

    void saveWorldData(WorldData worldData);

    void a(WorldData worldData, java.util.List players);

    PlayerFileData getPlayerFileData();

    void e();

    File getDataFile(String path);

    UUID getUUID();
}
