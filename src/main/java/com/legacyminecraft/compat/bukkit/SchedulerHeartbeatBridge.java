package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge for invoking CraftBukkit scheduler heartbeat hooks.
 */
public final class SchedulerHeartbeatBridge {
    private static final SchedulerHeartbeatBridge INSTANCE = new SchedulerHeartbeatBridge();
    private static final SchedulerWrapperProjectionBridgeBehaviour SCHEDULER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            SchedulerWrapperProjectionBridgeBehaviour.getInstance();

    private SchedulerHeartbeatBridge() {
    }

    public static SchedulerHeartbeatBridge getInstance() {
        return INSTANCE;
    }

    public void runMainThreadHeartbeat(BukkitScheduler scheduler, int currentTick) {
        CraftScheduler craftScheduler =
                SCHEDULER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftScheduler(scheduler);
        if (craftScheduler != null) {
            craftScheduler.mainThreadHeartbeat(currentTick);
        }
    }
}
