package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityTrackerWrapperThinnessTest {
    private static final Path ENTITY_TRACKER_PATH = Paths.get("src/main/java/net/minecraft/server/EntityTracker.java");

    @Test
    public void entityTrackerDelegatesCoreOperationsToCanonicalSystem() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_TRACKER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityTrackerSystem"));
        Assert.assertTrue(text.contains("trackerSystem.trackEntity"));
        Assert.assertTrue(text.contains("trackerSystem.registerEntity"));
        Assert.assertTrue(text.contains("trackerSystem.untrackEntity"));
        Assert.assertTrue(text.contains("trackerSystem.updatePlayers"));
        Assert.assertTrue(text.contains("trackerSystem.sendPacketToTracked"));
        Assert.assertTrue(text.contains("trackerSystem.sendPacketToTrackedAndSelf"));
        Assert.assertTrue(text.contains("trackerSystem.untrackPlayer"));
        Assert.assertTrue(text.contains("trackerSystem.onPlayerChunkLoad"));
        Assert.assertFalse(text.contains("new EntityTrackerEntry("));
    }
}
