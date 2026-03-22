package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.WorldLoaderSystem;
import net.minecraft.server.CompressedStreamTools;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldData;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WorldLoaderServiceTest {
    @Test
    public void ensureRootExistsCreatesMissingDirectory() {
        WorldLoaderSystem service = WorldLoaderSystem.getInstance();
        File root = new File(System.getProperty("java.io.tmpdir"), "poseidon-worldloader-root-" + System.nanoTime());
        Assert.assertFalse(root.exists());

        service.ensureRootExists(root);

        Assert.assertTrue(root.exists());
        Assert.assertTrue(root.isDirectory());
    }

    @Test
    public void loadWorldDataReadsPrimaryLevelDat() throws Exception {
        WorldLoaderSystem service = WorldLoaderSystem.getInstance();
        File root = createTempDir();
        File worldDir = new File(root, "world");
        worldDir.mkdirs();
        writeLevelFile(new File(worldDir, "level.dat"), "primary-world");

        WorldData worldData = service.loadWorldData(root, "world");

        Assert.assertNotNull(worldData);
        Assert.assertEquals("primary-world", worldData.name);
    }

    @Test
    public void loadWorldDataFallsBackToLevelDatOld() throws Exception {
        WorldLoaderSystem service = WorldLoaderSystem.getInstance();
        File root = createTempDir();
        File worldDir = new File(root, "world_old");
        worldDir.mkdirs();
        writeLevelFile(new File(worldDir, "level.dat_old"), "old-world");

        WorldData worldData = service.loadWorldData(root, "world_old");

        Assert.assertNotNull(worldData);
        Assert.assertEquals("old-world", worldData.name);
    }

    @Test
    public void deleteTreeRemovesNestedDirectoriesAndFiles() throws Exception {
        WorldLoaderSystem service = WorldLoaderSystem.getInstance();
        File root = createTempDir();
        File nestedDir = new File(root, "nested");
        nestedDir.mkdirs();
        File nestedFile = new File(nestedDir, "payload.txt");
        Assert.assertTrue(nestedFile.createNewFile());

        service.deleteTree(root.listFiles());

        Assert.assertFalse(nestedDir.exists());
    }

    private File createTempDir() {
        File dir = new File(System.getProperty("java.io.tmpdir"), "poseidon-worldloader-" + System.nanoTime());
        dir.mkdirs();
        return dir;
    }

    private void writeLevelFile(File target, String worldName) throws Exception {
        NBTTagCompound data = new NBTTagCompound();
        data.setLong("RandomSeed", 1L);
        data.a("SpawnX", 0);
        data.a("SpawnY", 64);
        data.a("SpawnZ", 0);
        data.a("SpawnYaw", 0.0F);
        data.a("SpawnPitch", 0.0F);
        data.setLong("Time", 0L);
        data.setLong("LastPlayed", 0L);
        data.setLong("SizeOnDisk", 0L);
        data.setString("LevelName", worldName);
        data.a("version", 19132);
        data.a("rainTime", 0);
        data.a("raining", false);
        data.a("thunderTime", 0);
        data.a("thundering", false);

        NBTTagCompound root = new NBTTagCompound();
        root.a("Data", data);

        FileOutputStream outputStream = new FileOutputStream(target);
        CompressedStreamTools.a(root, (OutputStream) outputStream);
        outputStream.close();
    }
}
