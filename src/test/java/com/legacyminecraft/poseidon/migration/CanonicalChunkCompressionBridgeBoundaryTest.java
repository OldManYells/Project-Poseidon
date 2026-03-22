package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalChunkCompressionBridgeBoundaryTest {
    private static final Path OUTBOUND_PACKET_DISPATCH_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/OutboundPacketDispatchSystem.java");
    private static final Path PLAYER_CHUNK_SYNC_COORDINATOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerChunkSyncCoordinator.java");

    @Test
    public void canonicalSystemsUseChunkCompressionBridgeInsteadOfDirectCraftBukkitThreadImport() throws IOException {
        String outboundPacketDispatchText = read(OUTBOUND_PACKET_DISPATCH_PATH);
        String playerChunkSyncCoordinatorText = read(PLAYER_CHUNK_SYNC_COORDINATOR_PATH);

        Assert.assertTrue(outboundPacketDispatchText.contains("ChunkCompressionDispatchBridge"));
        Assert.assertTrue(outboundPacketDispatchText.contains("chunkCompressionDispatchBridge.sendPacket(entityPlayer, packet);"));
        Assert.assertFalse(outboundPacketDispatchText.contains("org.bukkit.craftbukkit.ChunkCompressionThread"));

        Assert.assertTrue(playerChunkSyncCoordinatorText.contains("ChunkCompressionDispatchBridge"));
        Assert.assertTrue(playerChunkSyncCoordinatorText.contains("chunkCompressionDispatchBridge.getPlayerQueueSize(player)"));
        Assert.assertFalse(playerChunkSyncCoordinatorText.contains("org.bukkit.craftbukkit.ChunkCompressionThread"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

