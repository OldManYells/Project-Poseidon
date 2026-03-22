package com.legacyminecraft.poseidon.world;

import net.minecraft.server.CompressedStreamTools;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderHell;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Canonical filesystem and NBT persistence policy for legacy PlayerNBTManager wrappers.
 */
public final class PlayerNbtStorageSystem {
    private static final PlayerNbtStorageSystem INSTANCE = new PlayerNbtStorageSystem();

    private PlayerNbtStorageSystem() {
    }

    public static PlayerNbtStorageSystem getInstance() {
        return INSTANCE;
    }

    public void ensureStorageDirectories(File worldDirectory, File playersDirectory, File dataDirectory, boolean createPlayersDirectory) {
        worldDirectory.mkdirs();
        dataDirectory.mkdirs();
        if (createPlayersDirectory) {
            playersDirectory.mkdirs();
        }
    }

    public File resolveChunkStorageDirectory(File worldDirectory, WorldProvider worldprovider) {
        if (worldprovider instanceof WorldProviderHell) {
            File netherDirectory = new File(worldDirectory, "DIM-1");
            netherDirectory.mkdirs();
            return netherDirectory;
        }

        return worldDirectory;
    }

    public void savePlayerData(File playersDirectory, File playerDataFile, String username, NBTTagCompound playerTag, Logger logger) {
        try {
            File tempFile = new File(playersDirectory, "_tmp_.dat");
            CompressedStreamTools.a(playerTag, (OutputStream) (new FileOutputStream(tempFile)));
            if (playerDataFile.exists()) {
                playerDataFile.delete();
            }
            tempFile.renameTo(playerDataFile);
        } catch (Exception exception) {
            logger.warning("Failed to save player data for " + username);
        }
    }

    public NBTTagCompound loadPlayerData(File playerDataFile, String username, Logger logger) {
        try {
            if (playerDataFile.exists()) {
                return CompressedStreamTools.a((InputStream) (new FileInputStream(playerDataFile)));
            }
        } catch (Exception exception) {
            logger.warning("Failed to load player data for " + username);
        }

        return null;
    }
}
