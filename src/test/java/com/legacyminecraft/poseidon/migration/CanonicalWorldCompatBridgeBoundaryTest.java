package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalWorldCompatBridgeBoundaryTest {
    private static final Path WORLD_SERVER_CHUNK_PROVIDER_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/WorldServerChunkProviderBehaviour.java");
    private static final Path WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/map/WorldMapHumanTrackerBehaviour.java");
    private static final Path PLAYER_WORLD_TRANSFER_SUPPORT_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerWorldTransferSupport.java");
    private static final Path WORLD_CHUNK_GENERATOR_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/WorldChunkGeneratorBridgeBehaviour.java");
    private static final Path WORLD_MAP_RENDER_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/WorldMapRenderBridgeBehaviour.java");
    private static final Path PORTAL_TRAVEL_AGENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/PortalTravelAgentBridgeBehaviour.java");

    @Test
    public void canonicalWorldFlowsUseCompatBridgesForCraftBukkitAdapters() throws IOException {
        String worldServerChunkProviderText = read(WORLD_SERVER_CHUNK_PROVIDER_BEHAVIOUR_PATH);
        String worldMapHumanTrackerText = read(WORLD_MAP_HUMAN_TRACKER_BEHAVIOUR_PATH);
        String playerWorldTransferSupportText = read(PLAYER_WORLD_TRANSFER_SUPPORT_PATH);

        Assert.assertFalse(worldServerChunkProviderText.contains("import org.bukkit.craftbukkit."));
        Assert.assertFalse(worldMapHumanTrackerText.contains("import org.bukkit.craftbukkit."));
        Assert.assertFalse(playerWorldTransferSupportText.contains("import org.bukkit.craftbukkit."));

        Assert.assertTrue(worldServerChunkProviderText.contains("WorldChunkGeneratorBridgeBehaviour"));
        Assert.assertTrue(worldMapHumanTrackerText.contains("WorldMapRenderBridgeBehaviour"));
        Assert.assertTrue(playerWorldTransferSupportText.contains("PortalTravelAgentBridgeBehaviour"));
        Assert.assertTrue(playerWorldTransferSupportText.contains("WorldDimensionBridgeBehaviour"));

        String worldChunkGeneratorBridgeText = read(WORLD_CHUNK_GENERATOR_BRIDGE_BEHAVIOUR_PATH);
        String worldMapRenderBridgeText = read(WORLD_MAP_RENDER_BRIDGE_BEHAVIOUR_PATH);
        String portalTravelAgentBridgeText = read(PORTAL_TRAVEL_AGENT_BRIDGE_BEHAVIOUR_PATH);

        Assert.assertTrue(worldChunkGeneratorBridgeText.contains("import org.bukkit.craftbukkit.generator."));
        Assert.assertTrue(worldMapRenderBridgeText.contains("import org.bukkit.craftbukkit.entity.CraftPlayer;"));
        Assert.assertTrue(portalTravelAgentBridgeText.contains("new org.bukkit.craftbukkit.PortalTravelAgent()"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
