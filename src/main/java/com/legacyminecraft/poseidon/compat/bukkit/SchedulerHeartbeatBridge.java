package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.craftbukkit.scheduler.CraftScheduler;

/**
 * Canonical bridge for invoking CraftBukkit scheduler heartbeat hooks.
 */
public final class SchedulerHeartbeatBridge {
    private static final SchedulerHeartbeatBridge INSTANCE = new SchedulerHeartbeatBridge();

    private SchedulerHeartbeatBridge() {
    }

    public static SchedulerHeartbeatBridge getInstance() {
        return INSTANCE;
    }

    public void runMainThreadHeartbeat(BukkitScheduler scheduler, int currentTick) {
        if (scheduler instanceof CraftScheduler) {
            ((CraftScheduler) scheduler).mainThreadHeartbeat(currentTick);
        }
    }
}

