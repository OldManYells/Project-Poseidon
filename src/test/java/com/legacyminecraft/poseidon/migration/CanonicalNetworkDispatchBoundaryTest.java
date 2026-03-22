package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalNetworkDispatchBoundaryTest {
    private static final Path CHAT_MESSAGE_ROUTER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/ChatMessageRouter.java");
    private static final Path PLAYER_CHAT_DISPATCH_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PlayerChatDispatchSystem.java");
    private static final Path PLAYER_MOVE_EVENT_DISPATCH_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PlayerMoveEventDispatchSystem.java");
    private static final Path PLAYER_TELEPORT_COORDINATOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PlayerTeleportCoordinator.java");

    @Test
    public void canonicalNetworkDispatchersUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String chatMessageRouterText = read(CHAT_MESSAGE_ROUTER_PATH);
        String playerChatDispatchSystemText = read(PLAYER_CHAT_DISPATCH_SYSTEM_PATH);
        String playerMoveEventDispatchSystemText = read(PLAYER_MOVE_EVENT_DISPATCH_SYSTEM_PATH);
        String playerTeleportCoordinatorText = read(PLAYER_TELEPORT_COORDINATOR_PATH);

        assertUsesServerApi(chatMessageRouterText, "handleNonCommandChat(Server server, MinecraftServer minecraftServer, Player player, String message)");
        assertUsesServerApi(playerChatDispatchSystemText, "dispatchValidatedChat(\n            Server server,");
        assertUsesServerApi(playerMoveEventDispatchSystemText, "processMoveEvent(\n            Server server,");
        assertUsesServerApi(playerTeleportCoordinatorText, "resolveTeleportDestination(Server server, Player player, double x, double y, double z, float yaw, float pitch)");
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
