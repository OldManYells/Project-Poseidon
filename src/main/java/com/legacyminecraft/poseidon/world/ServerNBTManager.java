package com.legacyminecraft.poseidon.world;

import java.io.File;
import java.util.UUID;

/**
 * World-local NBT manager alias.
 */
public class ServerNBTManager extends com.legacyminecraft.compat.bukkit.ServerNBTManager implements IDataManager {
    private final File root;

    public ServerNBTManager(File basePath, String worldName, boolean createDirectories) {
        super(basePath, worldName, createDirectories);
        this.root = basePath;
    }

    @Override
    public WorldData getWorldData() {
        return null;
    }

    @Override
    public void checkSession() {
    }

    @Override
    public IChunkLoader createChunkLoader(WorldProvider worldProvider) {
        return null;
    }

    @Override
    public void saveWorldDataWithPlayer(WorldData worldData, NBTTagCompound playerData) {
    }

    @Override
    public void saveWorldData(WorldData worldData) {
    }

    @Override
    public void a(WorldData worldData, java.util.List players) {
    }

    @Override
    public PlayerFileData getPlayerFileData() {
        return new PlayerFileData();
    }

    @Override
    public void e() {
    }

    @Override
    public File getDataFile(String path) {
        return new File(root, path);
    }

    @Override
    public UUID getUUID() {
        return new UUID(0L, 0L);
    }
}
