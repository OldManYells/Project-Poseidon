package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalServerWorldSaveBoundaryTest {
    private static final Path SERVER_WORLD_SAVE_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerWorldSaveService.java");
    private static final Path SERVER_CHUNK_SAVE_COORDINATOR_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerChunkSaveCoordinatorService.java");
    private static final Path SERVER_CHUNK_SAVE_COORDINATOR_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerChunkSaveCoordinatorSystem.java");

    @Test
    public void worldSaveAndChunkSaveCoordinatorUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String worldSaveServiceText = read(SERVER_WORLD_SAVE_SERVICE_PATH);
        String chunkSaveCoordinatorServiceText = read(SERVER_CHUNK_SAVE_COORDINATOR_SERVICE_PATH);
        String chunkSaveCoordinatorSystemText = read(SERVER_CHUNK_SAVE_COORDINATOR_SYSTEM_PATH);

        Assert.assertTrue(worldSaveServiceText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(worldSaveServiceText.contains("saveWorldsAndEmitEvents(List<WorldServer> worlds, Server server)"));
        Assert.assertFalse(worldSaveServiceText.contains("org.bukkit.craftbukkit.CraftServer"));

        Assert.assertTrue(chunkSaveCoordinatorServiceText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(chunkSaveCoordinatorServiceText.contains("saveChunks(List worlds, Server server, SavePlayersAction savePlayersAction, Logger logger)"));
        Assert.assertFalse(chunkSaveCoordinatorServiceText.contains("org.bukkit.craftbukkit.CraftServer"));

        Assert.assertTrue(chunkSaveCoordinatorSystemText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(chunkSaveCoordinatorSystemText.contains("saveChunks(final List worlds, Server server, final SavePlayersAction savePlayersAction, Logger logger)"));
        Assert.assertFalse(chunkSaveCoordinatorSystemText.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

