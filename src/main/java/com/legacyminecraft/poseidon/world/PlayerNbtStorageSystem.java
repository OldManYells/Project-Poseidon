package com.legacyminecraft.poseidon.world;


import com.legacyminecraft.poseidon.compat.LegacyCompatGatewayRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

    public File resolveChunkStorageDirectory(File worldDirectory, Object worldprovider) {
        if (worldprovider != null && worldprovider.getClass().getSimpleName().equals("WorldProviderHell")) {
            File netherDirectory = new File(worldDirectory, "DIM-1");
            netherDirectory.mkdirs();
            return netherDirectory;
        }

        return worldDirectory;
    }

    public void savePlayerData(File playersDirectory, File playerDataFile, String username, Object playerTag, Logger logger) {
        try {
            File tempFile = new File(playersDirectory, "_tmp_.dat");
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                LegacyCompatGatewayRegistry.gateway().writeCompressed(playerTag, outputStream);
            }
            if (playerDataFile.exists()) {
                playerDataFile.delete();
            }
            tempFile.renameTo(playerDataFile);
        } catch (Exception exception) {
            logger.warning("Failed to save player data for " + username);
        }
    }

    public <T> T loadPlayerData(File playerDataFile, String username, Logger logger) {
        try {
            if (playerDataFile.exists()) {
                try (FileInputStream inputStream = new FileInputStream(playerDataFile)) {
                    return WorldBridgeReflection.cast(LegacyCompatGatewayRegistry.gateway().readCompressed(inputStream));
                }
            }
        } catch (Exception exception) {
            logger.warning("Failed to load player data for " + username);
        }

        return null;
    }
}
