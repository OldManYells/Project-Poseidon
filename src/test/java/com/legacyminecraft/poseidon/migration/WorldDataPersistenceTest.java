package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.WorldDataPersistence;
import net.minecraft.server.WorldData;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class WorldDataPersistenceTest {
    @Test
    public void savesAndLoadsWorldData() throws Exception {
        File tempDir = createTempDirectory("poseidon-worlddata");
        WorldDataPersistence persistence = WorldDataPersistence.getInstance();

        WorldData original = new WorldData(12345L, "MigrationWorld");
        original.setSpawn(10, 64, 10, 90.0F, 0.0F);
        original.a(19133);
        original.a(4567L);

        persistence.saveWorldData(tempDir, original);
        WorldData loaded = persistence.loadWorldData(tempDir);

        Assert.assertNotNull(loaded);
        Assert.assertEquals(12345L, loaded.getSeed());
        Assert.assertEquals("MigrationWorld", loaded.name);
        Assert.assertEquals(10, loaded.c());
        Assert.assertEquals(64, loaded.d());
        Assert.assertEquals(10, loaded.e());
        Assert.assertEquals(90.0F, loaded.getYaw(), 0.01F);
    }

    private static File createTempDirectory(String prefix) throws Exception {
        File temp = File.createTempFile(prefix, "");
        if (!temp.delete()) {
            throw new IllegalStateException("Failed to prepare temp dir");
        }
        if (!temp.mkdirs()) {
            throw new IllegalStateException("Failed to create temp dir");
        }
        temp.deleteOnExit();
        return temp;
    }
}
