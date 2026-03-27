package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyPortalTravelAgentWrapperThinnessTest {
    private static final Path PORTAL_TRAVEL_AGENT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/PortalTravelAgent.java");

    @Test
    public void portalTravelAgentDelegatesFindPortalSearchToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PORTAL_TRAVEL_AGENT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PortalSearchBehaviour"));
        Assert.assertTrue(text.contains("PORTAL_SEARCH_BEHAVIOUR"));
        Assert.assertTrue(text.contains("findNearestPortal("));
        Assert.assertTrue(text.contains("searchResult.getPortalCenterX()"));
        Assert.assertTrue(text.contains("searchResult.getPortalCenterY()"));
        Assert.assertTrue(text.contains("searchResult.getPortalCenterZ()"));
        Assert.assertFalse(text.contains("for (int j1 = l - this.searchRadius; j1 <= l + this.searchRadius; ++j1)"));
        Assert.assertFalse(text.contains("while (world.getTypeId(j1, l1 - 1, k1) == Block.PORTAL.id)"));
        Assert.assertFalse(text.contains("return new Location(location.getWorld(), d5, d6, d1, location.getYaw(), location.getPitch());"));
    }

    @Test
    public void portalTravelAgentDelegatesCreatePortalToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(PORTAL_TRAVEL_AGENT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PortalCreationBehaviour"));
        Assert.assertTrue(text.contains("PORTAL_CREATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("WorldHandleBridgeBehaviour"));
        Assert.assertTrue(text.contains("WORLD_HANDLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveWorldServerHandle(location.getWorld())"));
        Assert.assertTrue(text.contains("PORTAL_CREATION_BEHAVIOUR.createPortal("));
        Assert.assertFalse(text.contains("label271:"));
        Assert.assertFalse(text.contains("PortalCreateEvent event ="));
        Assert.assertFalse(text.contains("world.suppressPhysics = true;"));
        Assert.assertFalse(text.contains("((CraftWorld) location.getWorld()).getHandle()"));
        Assert.assertFalse(text.contains("(CraftWorld) location.getWorld()"));
    }
}
