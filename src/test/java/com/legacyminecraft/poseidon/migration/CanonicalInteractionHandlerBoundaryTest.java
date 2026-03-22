package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalInteractionHandlerBoundaryTest {
    private static final Path HOTBAR_SELECTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/inventory/HotbarSelectionBehaviour.java");
    private static final Path PLAYER_ACTION_PACKET_HANDLER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/PlayerActionPacketHandler.java");
    private static final Path ENTITY_INTERACTION_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/EntityInteractionSystem.java");
    private static final Path BLOCK_INTERACTION_PACKET_HANDLER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/block/BlockInteractionPacketHandler.java");
    private static final Path SIGN_UPDATE_PROCESSOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/block/SignUpdateProcessor.java");

    @Test
    public void canonicalInteractionHandlersUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String hotbarSelectionText = read(HOTBAR_SELECTION_BEHAVIOUR_PATH);
        String playerActionText = read(PLAYER_ACTION_PACKET_HANDLER_PATH);
        String entityInteractionText = read(ENTITY_INTERACTION_SYSTEM_PATH);
        String blockInteractionText = read(BLOCK_INTERACTION_PACKET_HANDLER_PATH);
        String signUpdateText = read(SIGN_UPDATE_PROCESSOR_PATH);

        assertUsesServerApi(hotbarSelectionText, "handleSwitch(Server server, EntityPlayer player, Packet16BlockItemSwitch");
        assertUsesServerApi(playerActionText, "handleArmAnimationPacket(Server server, EntityPlayer player, Packet18ArmAnimation");
        assertUsesServerApi(playerActionText, "handleEntityActionPacket(Server server, EntityPlayer player, Packet19EntityAction");
        assertUsesServerApi(entityInteractionText, "handleUseEntityPacket(MinecraftServer minecraftServer, Server server, EntityPlayer player, Packet7UseEntity");
        assertUsesServerApi(blockInteractionText, "handleBlockDig(\n            Server server,");
        assertUsesServerApi(signUpdateText, "processSignUpdate(MinecraftServer minecraftServer, Server server, EntityPlayer player, Packet130UpdateSign");

        Assert.assertFalse(playerActionText.contains("org.bukkit.craftbukkit.event.CraftEventFactory"));
        Assert.assertFalse(blockInteractionText.contains("org.bukkit.craftbukkit.event.CraftEventFactory"));
        Assert.assertFalse(signUpdateText.contains("org.bukkit.craftbukkit.block.CraftBlock"));
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
