package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.LegacyDimensionFolderMigrationHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;

public class LegacyDimensionFolderMigrationServiceTest {
    @Test
    public void migrationNotRequiredWhenLegacyFolderMissing() throws Exception {
        LegacyDimensionFolderMigrationHandler service = LegacyDimensionFolderMigrationHandler.getInstance();
        File root = Files.createTempDirectory("poseidon-migration-not-required").toFile();
        File oldWorld = new File(root, "world/DIM-1");
        File newWorld = new File(root, "world_nether/DIM-1");

        LegacyDimensionFolderMigrationHandler.MigrationStatus status =
                service.migrateLegacyDimensionFolder(oldWorld, newWorld);
        Assert.assertEquals(LegacyDimensionFolderMigrationHandler.MigrationStatus.NOT_REQUIRED, status);
    }

    @Test
    public void targetExistsStatusWhenDestinationAlreadyExistsAsFile() throws Exception {
        LegacyDimensionFolderMigrationHandler service = LegacyDimensionFolderMigrationHandler.getInstance();
        File root = Files.createTempDirectory("poseidon-migration-target-exists").toFile();
        File oldWorld = new File(root, "world/DIM-1");
        Assert.assertTrue(oldWorld.mkdirs());
        File newWorld = new File(root, "world_nether/DIM-1");
        Assert.assertTrue(newWorld.getParentFile().mkdirs());
        Assert.assertTrue(newWorld.createNewFile());

        LegacyDimensionFolderMigrationHandler.MigrationStatus status =
                service.migrateLegacyDimensionFolder(oldWorld, newWorld);
        Assert.assertEquals(LegacyDimensionFolderMigrationHandler.MigrationStatus.TARGET_EXISTS, status);
    }

    @Test
    public void createPathFailedStatusMirrorsLegacyMkdirsCheck() throws Exception {
        LegacyDimensionFolderMigrationHandler service = LegacyDimensionFolderMigrationHandler.getInstance();
        File root = Files.createTempDirectory("poseidon-migration-create-path").toFile();
        File oldWorld = new File(root, "world/DIM-1");
        Assert.assertTrue(oldWorld.mkdirs());
        File newWorld = new File(root, "world_nether/DIM-1");
        Assert.assertTrue(newWorld.getParentFile().mkdirs());

        LegacyDimensionFolderMigrationHandler.MigrationStatus status =
                service.migrateLegacyDimensionFolder(oldWorld, newWorld);
        Assert.assertEquals(LegacyDimensionFolderMigrationHandler.MigrationStatus.CREATE_PATH_FAILED, status);
    }

    @Test
    public void movedStatusWhenLegacyFolderRenameSucceeds() throws Exception {
        LegacyDimensionFolderMigrationHandler service = LegacyDimensionFolderMigrationHandler.getInstance();
        File root = Files.createTempDirectory("poseidon-migration-moved").toFile();
        File oldWorld = new File(root, "world/DIM-1");
        Assert.assertTrue(oldWorld.mkdirs());
        File marker = new File(oldWorld, "marker.txt");
        Assert.assertTrue(marker.createNewFile());
        File newWorld = new File(root, "world_nether/DIM-1");

        LegacyDimensionFolderMigrationHandler.MigrationStatus status =
                service.migrateLegacyDimensionFolder(oldWorld, newWorld);
        Assert.assertEquals(LegacyDimensionFolderMigrationHandler.MigrationStatus.MOVED, status);
        Assert.assertFalse(oldWorld.exists());
        Assert.assertTrue(newWorld.exists());
        Assert.assertTrue(new File(newWorld, "marker.txt").exists());
    }
}
