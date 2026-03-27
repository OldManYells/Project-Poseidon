package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat scheduler contract.
 */
public interface BukkitScheduler {
    void mainThreadHeartbeat(int currentTick);

    int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delayTicks);

    int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delayTicks, long periodTicks);

    java.util.List<BukkitWorker> getActiveWorkers();
}
