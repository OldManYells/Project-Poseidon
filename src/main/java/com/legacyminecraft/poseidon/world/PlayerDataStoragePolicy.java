package com.legacyminecraft.poseidon.world;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.poseidon.auth.uuid.UUIDManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Canonical policy for player-data storage layout and migration.
 */
public final class PlayerDataStoragePolicy {
    private static final PlayerDataStoragePolicy INSTANCE = new PlayerDataStoragePolicy();

    private PlayerDataStoragePolicy() {
    }

    public static PlayerDataStoragePolicy getInstance() {
        return INSTANCE;
    }

    public boolean isUuidStorageEnabled() {
        return (boolean) PoseidonConfig.getInstance().getConfigOption("settings.save-playerdata-by-uuid");
    }

    public File getPlayerDataFile(File playersDirectory, String username) {
        if (isUuidStorageEnabled()) {
            return new File(playersDirectory, UUIDManager.getInstance().getUUIDGraceful(username) + ".dat");
        }
        return new File(playersDirectory, username + ".dat");
    }

    public void migrateNamedDataToUuidStorageIfRequired(File playersDirectory, String username) throws IOException {
        if (!isUuidStorageEnabled()) {
            return;
        }

        File uuidFile = getPlayerDataFile(playersDirectory, username);
        if (uuidFile.exists()) {
            return;
        }

        File namedFile = new File(playersDirectory, username + ".dat");
        if (!namedFile.exists()) {
            return;
        }

        copyFileUsingStream(namedFile, uuidFile);
        File backupFile = new File(playersDirectory, username + ".datbackup");
        namedFile.renameTo(backupFile);
        System.out.println("Converting playerdata for " + username + " to a UUID");
    }

    private static void copyFileUsingStream(File source, File destination) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
