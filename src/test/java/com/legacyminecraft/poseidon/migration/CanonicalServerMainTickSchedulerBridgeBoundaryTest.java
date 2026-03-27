package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalServerMainTickSchedulerBridgeBoundaryTest {
    private static final Path SERVER_MAIN_TICK_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerMainTickService.java");
    private static final Path SCHEDULER_HEARTBEAT_BRIDGE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/SchedulerHeartbeatBridge.java");

    @Test
    public void serverMainTickServiceUsesSchedulerHeartbeatBridgeInsteadOfDirectCraftSchedulerCast() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_MAIN_TICK_SERVICE_PATH), StandardCharsets.UTF_8);
        String bridgeText = new String(Files.readAllBytes(SCHEDULER_HEARTBEAT_BRIDGE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerHeartbeatBridge"));
        Assert.assertTrue(text.contains("schedulerHeartbeatBridge.runMainThreadHeartbeat(server.server.getScheduler(), nextTicks);"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.scheduler.CraftScheduler"));
        Assert.assertFalse(text.contains("((CraftScheduler) server.server.getScheduler()).mainThreadHeartbeat(nextTicks);"));
        Assert.assertTrue(bridgeText.contains("SchedulerWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(bridgeText.contains("SCHEDULER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftScheduler(scheduler)"));
        Assert.assertFalse(bridgeText.contains("if (scheduler instanceof CraftScheduler)"));
        Assert.assertFalse(bridgeText.contains("((CraftScheduler) scheduler).mainThreadHeartbeat(currentTick);"));
    }
}
