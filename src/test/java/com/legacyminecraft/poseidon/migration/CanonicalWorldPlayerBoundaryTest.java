package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalWorldPlayerBoundaryTest {
    private static final Path PLAYER_LIFECYCLE_COORDINATOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerLifecycleCoordinator.java");
    private static final Path PLAYER_WORLD_MOVE_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerWorldMoveSystem.java");
    private static final Path PLAYER_WORLD_TRANSFER_SUPPORT_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerWorldTransferSupport.java");
    private static final Path WORLD_MAP_PERSISTENCE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/map/WorldMapPersistenceBehaviour.java");

    @Test
    public void canonicalWorldPlayerFlowsUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String playerLifecycleCoordinatorText = read(PLAYER_LIFECYCLE_COORDINATOR_PATH);
        String playerWorldMoveSystemText = read(PLAYER_WORLD_MOVE_SYSTEM_PATH);
        String playerWorldTransferSupportText = read(PLAYER_WORLD_TRANSFER_SUPPORT_PATH);
        String worldMapPersistenceBehaviourText = read(WORLD_MAP_PERSISTENCE_BEHAVIOUR_PATH);

        assertUsesServerApi(playerLifecycleCoordinatorText, "onPlayerJoin(MinecraftServer server, Server bukkitServer, List players, EntityPlayer entityplayer, String joinMessageTemplate)");
        assertUsesServerApi(playerLifecycleCoordinatorText, "onPlayerDisconnect(MinecraftServer server, Server bukkitServer, PlayerFileData playerFileData, List players, EntityPlayer entityplayer, String leaveMessageTemplate)");
        assertUsesServerApi(playerWorldMoveSystemText, "MinecraftServer server,\n            Server bukkitServer,");
        assertUsesServerApi(playerWorldTransferSupportText, "Server bukkitServer,");
        assertUsesServerApi(worldMapPersistenceBehaviourText, "loadFromNbt(NBTTagCompound mapTag, Server server)");
        assertUsesServerApi(worldMapPersistenceBehaviourText, "writeToNbt(NBTTagCompound mapTag, Server server, byte dimension, int xCenter, int zCenter, byte scale, byte[] colors, UUID uniqueId)");
        Assert.assertFalse(playerWorldTransferSupportText.contains("org.bukkit.craftbukkit.CraftWorld"));
        Assert.assertFalse(playerWorldTransferSupportText.contains("org.bukkit.craftbukkit.PortalTravelAgent"));
    }

    private static void assertUsesServerApi(String text, String signatureFragment) {
        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertTrue(text.contains(signatureFragment));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
