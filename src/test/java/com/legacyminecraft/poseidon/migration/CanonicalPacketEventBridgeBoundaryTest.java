package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalPacketEventBridgeBoundaryTest {
    private static final Path PACKET_EVENT_BRIDGE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/PacketEventBridge.java");
    private static final Path INCOMING_PACKET_EVENT_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/IncomingPacketEventSystem.java");

    @Test
    public void packetEventBridgeUsesBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String packetEventBridgeText = read(PACKET_EVENT_BRIDGE_PATH);
        String incomingPacketEventSystemText = read(INCOMING_PACKET_EVENT_SYSTEM_PATH);

        Assert.assertTrue(packetEventBridgeText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(packetEventBridgeText.contains("allowIncomingPacket(Server server, EntityPlayer player, Packet packet)"));
        Assert.assertFalse(packetEventBridgeText.contains("org.bukkit.craftbukkit.CraftServer"));

        Assert.assertTrue(incomingPacketEventSystemText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(incomingPacketEventSystemText.contains("allowIncomingPacket(Server server, EntityPlayer player, Packet packet)"));
        Assert.assertFalse(incomingPacketEventSystemText.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

