package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.PlayerNbtStorageSystem;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderHell;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.logging.Logger;

public class PlayerNbtStorageServiceTest {
    private static final Logger LOGGER = Logger.getLogger("PlayerNbtStorageServiceTest");
    private final PlayerNbtStorageSystem service = PlayerNbtStorageSystem.getInstance();

    @Test
    public void createsExpectedStorageDirectories() throws Exception {
        File root = Files.createTempDirectory("poseidon-storage").toFile();
        File worldDirectory = new File(root, "world");
        File playersDirectory = new File(worldDirectory, "players");
        File dataDirectory = new File(worldDirectory, "data");

        service.ensureStorageDirectories(worldDirectory, playersDirectory, dataDirectory, true);

        Assert.assertTrue(worldDirectory.exists());
        Assert.assertTrue(playersDirectory.exists());
        Assert.assertTrue(dataDirectory.exists());
    }

    @Test
    public void resolvesNetherChunkStorageDirectory() throws Exception {
        File root = Files.createTempDirectory("poseidon-nbt-world").toFile();
        WorldProvider provider = new WorldProviderHell();

        File resolved = service.resolveChunkStorageDirectory(root, provider);

        Assert.assertEquals(new File(root, "DIM-1").getCanonicalPath(), resolved.getCanonicalPath());
        Assert.assertTrue(resolved.exists());
    }

    @Test
    public void savesAndLoadsPlayerTag() throws Exception {
        File root = Files.createTempDirectory("poseidon-player-tag").toFile();
        File playersDirectory = new File(root, "players");
        playersDirectory.mkdirs();
        File playerFile = new File(playersDirectory, "tester.dat");

        NBTTagCompound original = new NBTTagCompound();
        original.a("score", 42);
        original.setString("name", "tester");

        service.savePlayerData(playersDirectory, playerFile, "tester", original, LOGGER);
        NBTTagCompound loaded = service.loadPlayerData(playerFile, "tester", LOGGER);

        Assert.assertNotNull(loaded);
        Assert.assertEquals(42, loaded.e("score"));
        Assert.assertEquals("tester", loaded.getString("name"));
    }
}
