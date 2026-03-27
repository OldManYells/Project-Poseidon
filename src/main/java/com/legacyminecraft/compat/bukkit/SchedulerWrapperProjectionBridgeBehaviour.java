package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit schedulers to CraftScheduler wrappers.
 */
public final class SchedulerWrapperProjectionBridgeBehaviour {
    private static final SchedulerWrapperProjectionBridgeBehaviour INSTANCE =
            new SchedulerWrapperProjectionBridgeBehaviour();

    private SchedulerWrapperProjectionBridgeBehaviour() {
    }

    public static SchedulerWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftScheduler resolveCraftScheduler(BukkitScheduler scheduler) {
        if (scheduler instanceof CraftScheduler) {
            return (CraftScheduler) scheduler;
        }
        return null;
    }
}
