package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNmsPortalTravelAgentWrapperThinnessTest {
    private static final Path NMS_PORTAL_TRAVEL_AGENT_PATH =
            Paths.get("src/main/java/net/minecraft/server/PortalTravelAgent.java");

    @Test
    public void nmsPortalTravelAgentDelegatesSearchPolicyAndCreateEventBridge() throws IOException {
        String text = new String(Files.readAllBytes(NMS_PORTAL_TRAVEL_AGENT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PortalTravelSearchBehaviour"));
        Assert.assertTrue(text.contains("PortalCreateEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.defaultSearchRadius()"));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.defaultCreateSearchRadius()"));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.axisDistance("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.centeredCoordinate("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.squaredDistance("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.isBetterDistance("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.adjustPortalCenterX("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.adjustPortalCenterZ("));
        Assert.assertTrue(text.contains("PORTAL_TRAVEL_SEARCH_BEHAVIOUR.clampPortalBaseY(i1)"));
        Assert.assertTrue(text.contains("PORTAL_CREATE_EVENT_BRIDGE_BEHAVIOUR.shouldCancelPortalCreation(blocks, bworld)"));

        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.CraftWorld;"));
        Assert.assertFalse(text.contains("CraftEventFactory"));
    }
}

