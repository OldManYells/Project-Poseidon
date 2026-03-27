package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.List;

/**
 * Canonical compat craft-scheduler scaffold.
 */
public class CraftScheduler implements BukkitScheduler {
    @Override
    public void mainThreadHeartbeat(int currentTick) {
    }

    @Override
    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delayTicks) {
        if (task != null) {
            task.run();
        }
        return 0;
    }

    @Override
    public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delayTicks, long periodTicks) {
        if (task != null) {
            task.run();
        }
        return 0;
    }

    @Override
    public List<BukkitWorker> getActiveWorkers() {
        return new ArrayList<BukkitWorker>();
    }
}
