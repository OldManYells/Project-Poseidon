package com.legacyminecraft.compat.bukkit;

import com.legacyminecraft.poseidon.utility.PerformanceStatistic;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical system for CraftScheduler main-thread heartbeat queue transfer and synced task execution.
 */
public final class CraftSchedulerMainThreadHeartbeatSystem {
    private static final CraftSchedulerMainThreadHeartbeatSystem INSTANCE =
            new CraftSchedulerMainThreadHeartbeatSystem();

    private CraftSchedulerMainThreadHeartbeatSystem() {
    }

    public static CraftSchedulerMainThreadHeartbeatSystem getInstance() {
        return INSTANCE;
    }

    public void runHeartbeat(MainThreadHeartbeatAccess access, long currentTick) {
        if (!access.tryLockSyncedTasks()) {
            return;
        }

        try {
            if (access.tryLockMainThread()) {
                try {
                    access.setCurrentTick(currentTick);
                    access.transferMainThreadQueueToSyncedTasks();
                } finally {
                    access.unlockMainThread();
                }
            }

            long heartbeatDeadline = System.currentTimeMillis() + 35L;
            while (!access.isSyncedTasksEmpty() && System.currentTimeMillis() <= heartbeatDeadline) {
                CraftTask task = access.removeFirstSyncedTask();
                long taskStartTime = System.currentTimeMillis();
                try {
                    access.runSyncedTask(task);
                    reportTaskExecution(access, task, taskStartTime);
                } catch (Throwable taskFailure) {
                    access.getTaskLogger().log(
                            Level.WARNING,
                            "Task of '" + task.getOwner().getDescription().getName() + "' generated an exception",
                            taskFailure
                    );
                    access.removeScheduledTask(task);
                }
            }
        } finally {
            access.unlockSyncedTasks();
        }
    }

    private void reportTaskExecution(MainThreadHeartbeatAccess access, CraftTask task, long taskStartTime) {
        if (!access.isTaskPerformanceEnabled()) {
            return;
        }

        long taskDuration = System.currentTimeMillis() - taskStartTime;
        String taskKey = resolveTaskKey(task);

        Map<String, PerformanceStatistic> taskPerformance = access.getTaskPerformance();
        taskPerformance.computeIfAbsent(taskKey, key -> new PerformanceStatistic()).update(taskDuration);

        if (access.isPrintOnSlowTaskEnabled() && taskDuration > access.getPrintOnSlowTaskThreshold()) {
            access.getServerLogger().log(Level.WARNING, String.format(
                    "[Poseidon] Synchronous task from plugin %s took %d milliseconds. Statistics: %s",
                    taskKey,
                    taskDuration,
                    taskPerformance.get(taskKey).printStats()
            ));
        }
    }

    private String resolveTaskKey(CraftTask task) {
        if (task == null
                || task.getOwner() == null
                || task.getOwner().getDescription() == null
                || task.getOwner().getDescription().getName() == null) {
            return "Unknown";
        }

        return task.getOwner().getDescription().getName();
    }

    public interface MainThreadHeartbeatAccess {
        boolean tryLockSyncedTasks();

        void unlockSyncedTasks();

        boolean tryLockMainThread();

        void unlockMainThread();

        void setCurrentTick(long currentTick);

        void transferMainThreadQueueToSyncedTasks();

        boolean isSyncedTasksEmpty();

        CraftTask removeFirstSyncedTask();

        void runSyncedTask(CraftTask task);

        void removeScheduledTask(CraftTask task);

        boolean isTaskPerformanceEnabled();

        Map<String, PerformanceStatistic> getTaskPerformance();

        boolean isPrintOnSlowTaskEnabled();

        int getPrintOnSlowTaskThreshold();

        Logger getTaskLogger();

        Logger getServerLogger();
    }
}
