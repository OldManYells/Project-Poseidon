package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyEntityTrackerEntryWrapperThinnessTest {
    private static final Path ENTITY_TRACKER_ENTRY_PATH = Paths.get("src/main/java/net/minecraft/server/EntityTrackerEntry.java");

    @Test
    public void entityTrackerEntryDelegatesFrameDecisionAndStateWiringToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(ENTITY_TRACKER_ENTRY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityTrackingFrameBehaviour"));
        Assert.assertTrue(text.contains("trackingFrameBehaviour.decideFrame("));
        Assert.assertTrue(text.contains("trackingFrameBehaviour.captureRescanAnchor("));
        Assert.assertTrue(text.contains("trackingFrameBehaviour.createTrackingState("));
        Assert.assertTrue(text.contains("trackingFrameBehaviour.processVelocityChangeIfNeeded("));
        Assert.assertFalse(text.contains("trackingTickPolicy.shouldRescanTrackedPlayers("));
        Assert.assertFalse(text.contains("int nextTickCount = this.l + 1;"));
        Assert.assertFalse(text.contains("if (this.tracker.velocityChanged) {"));
    }
}
