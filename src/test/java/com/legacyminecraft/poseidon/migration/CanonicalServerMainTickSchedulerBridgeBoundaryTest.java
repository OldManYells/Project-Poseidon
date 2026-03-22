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

    @Test
    public void serverMainTickServiceUsesSchedulerHeartbeatBridgeInsteadOfDirectCraftSchedulerCast() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_MAIN_TICK_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("SchedulerHeartbeatBridge"));
        Assert.assertTrue(text.contains("schedulerHeartbeatBridge.runMainThreadHeartbeat(server.server.getScheduler(), nextTicks);"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.scheduler.CraftScheduler"));
        Assert.assertFalse(text.contains("((CraftScheduler) server.server.getScheduler()).mainThreadHeartbeat(nextTicks);"));
    }
}

