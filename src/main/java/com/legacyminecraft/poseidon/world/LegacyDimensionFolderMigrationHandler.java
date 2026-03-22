package com.legacyminecraft.poseidon.world;

import java.io.File;
import java.util.logging.Logger;

/**
 * Canonical handler for legacy dimension folder migration during world bootstrap.
 */
public final class LegacyDimensionFolderMigrationHandler {
    private static final LegacyDimensionFolderMigrationHandler INSTANCE = new LegacyDimensionFolderMigrationHandler();

    private LegacyDimensionFolderMigrationHandler() {
    }

    public static LegacyDimensionFolderMigrationHandler getInstance() {
        return INSTANCE;
    }

    public MigrationStatus migrateLegacyDimensionFolder(File oldWorld, File newWorld) {
        if (newWorld.isDirectory() || !oldWorld.isDirectory()) {
            return MigrationStatus.NOT_REQUIRED;
        }

        if (newWorld.exists()) {
            return MigrationStatus.TARGET_EXISTS;
        }

        if (!newWorld.getParentFile().mkdirs()) {
            return MigrationStatus.CREATE_PATH_FAILED;
        }

        if (!oldWorld.renameTo(newWorld)) {
            return MigrationStatus.MOVE_FAILED;
        }

        return MigrationStatus.MOVED;
    }

    public void migrateAndLog(Logger log, String worldType, File oldWorld, File newWorld) {
        MigrationStatus status = migrateLegacyDimensionFolder(oldWorld, newWorld);
        if (status == MigrationStatus.NOT_REQUIRED) {
            return;
        }

        log.info("---- Migration of old " + worldType + " folder required ----");
        log.info("Unfortunately due to the way that Minecraft implemented multiworld support in 1.6, Bukkit requires that you move your " + worldType + " folder to a new location in order to operate correctly.");
        log.info("We will move this folder for you, but it will mean that you need to move it back should you wish to stop using Bukkit in the future.");
        log.info("Attempting to move " + oldWorld + " to " + newWorld + "...");

        if (status == MigrationStatus.MOVED) {
            log.info("Success! To restore the nether in the future, simply move " + newWorld + " to " + oldWorld);
            log.info("---- Migration of old " + worldType + " folder complete ----");
            return;
        }

        if (status == MigrationStatus.TARGET_EXISTS) {
            log.severe("A file or folder already exists at " + newWorld + "!");
        } else if (status == MigrationStatus.MOVE_FAILED) {
            log.severe("Could not move folder " + oldWorld + " to " + newWorld + "!");
        } else if (status == MigrationStatus.CREATE_PATH_FAILED) {
            log.severe("Could not create path for " + newWorld + "!");
        }
        log.info("---- Migration of old " + worldType + " folder failed ----");
    }

    public enum MigrationStatus {
        NOT_REQUIRED,
        TARGET_EXISTS,
        CREATE_PATH_FAILED,
        MOVE_FAILED,
        MOVED
    }
}
