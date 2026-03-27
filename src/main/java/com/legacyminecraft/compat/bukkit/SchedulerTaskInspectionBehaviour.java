package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * Canonical behavior for CraftBukkit scheduler task inspection flows.
 */
public final class SchedulerTaskInspectionBehaviour {
    private static final SchedulerTaskInspectionBehaviour INSTANCE = new SchedulerTaskInspectionBehaviour();

    private SchedulerTaskInspectionBehaviour() {
    }

    public static SchedulerTaskInspectionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isCurrentlyRunning(Set<CraftWorker> workers, int taskId) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                if (worker.getTaskId() == taskId) {
                    return worker.isAlive();
                }
            }
            return false;
        }
    }

    public boolean isQueued(Map<CraftTask, Boolean> schedulerQueue, int taskId) {
        synchronized (schedulerQueue) {
            for (CraftTask current : schedulerQueue.keySet()) {
                if (current.getTaskId() == taskId) {
                    return true;
                }
            }
            return false;
        }
    }

    public List<BukkitWorker> getActiveWorkers(Set<CraftWorker> workers) {
        synchronized (workers) {
            List<BukkitWorker> workerList = new ArrayList<BukkitWorker>(workers.size());
            for (CraftWorker worker : workers) {
                workerList.add(worker);
            }
            return workerList;
        }
    }

    public List<BukkitTask> getPendingTasks(
            Collection<CraftTask> mainThreadQueue,
            Collection<CraftTask> syncedTasks,
            Map<CraftTask, Boolean> schedulerQueue,
            Lock mainThreadLock,
            Lock syncedTasksLock
    ) {
        List<CraftTask> taskList;
        syncedTasksLock.lock();
        try {
            synchronized (schedulerQueue) {
                mainThreadLock.lock();
                try {
                    taskList = new ArrayList<CraftTask>(
                            mainThreadQueue.size() + syncedTasks.size() + schedulerQueue.size()
                    );
                    taskList.addAll(mainThreadQueue);
                    taskList.addAll(syncedTasks);
                    taskList.addAll(schedulerQueue.keySet());
                } finally {
                    mainThreadLock.unlock();
                }
            }
        } finally {
            syncedTasksLock.unlock();
        }

        List<BukkitTask> newTaskList = new ArrayList<BukkitTask>(taskList.size());
        for (CraftTask craftTask : taskList) {
            newTaskList.add(craftTask);
        }
        return newTaskList;
    }
}
