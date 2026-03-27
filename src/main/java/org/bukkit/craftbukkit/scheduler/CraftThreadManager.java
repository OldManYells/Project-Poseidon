package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.compat.bukkit.SchedulerWorkerRegistryBehaviour;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;

public class CraftThreadManager {

    private final SchedulerWorkerRegistryBehaviour schedulerWorkerRegistryBehaviour =
            SchedulerWorkerRegistryBehaviour.getInstance();
    final HashSet<CraftWorker> workers = new HashSet<CraftWorker>();

    void executeTask(Runnable task, Plugin owner, int taskId) {

        CraftWorker craftWorker = new CraftWorker(this, task, owner, taskId);
        schedulerWorkerRegistryBehaviour.registerWorker(workers, craftWorker);

    }

    void interruptTask(int taskId) {
        schedulerWorkerRegistryBehaviour.interruptTask(workers, taskId);
    }

    void interruptTasks(Plugin owner) {
        schedulerWorkerRegistryBehaviour.interruptTasks(workers, owner);
    }

    void interruptAllTasks() {
        schedulerWorkerRegistryBehaviour.interruptAllTasks(workers);
    }

    boolean isAlive(int taskId) {
        return schedulerWorkerRegistryBehaviour.isAlive(workers, taskId);
    }

    void removeWorker(CraftWorker worker) {
        schedulerWorkerRegistryBehaviour.removeWorker(workers, worker);
    }
}
