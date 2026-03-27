package com.legacyminecraft.compat.bukkit;


import java.util.Set;

/**
 * Canonical behavior for CraftBukkit scheduler worker registry operations.
 */
public final class SchedulerWorkerRegistryBehaviour {
    private static final SchedulerWorkerRegistryBehaviour INSTANCE = new SchedulerWorkerRegistryBehaviour();

    private SchedulerWorkerRegistryBehaviour() {
    }

    public static SchedulerWorkerRegistryBehaviour getInstance() {
        return INSTANCE;
    }

    public void registerWorker(Set<CraftWorker> workers, CraftWorker worker) {
        synchronized (workers) {
            workers.add(worker);
        }
    }

    public void interruptTask(Set<CraftWorker> workers, int taskId) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                if (worker.getTaskId() == taskId) {
                    worker.interrupt();
                }
            }
        }
    }

    public void interruptTasks(Set<CraftWorker> workers, Plugin owner) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                if (worker.getOwner().equals(owner)) {
                    worker.interrupt();
                }
            }
        }
    }

    public void interruptAllTasks(Set<CraftWorker> workers) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                worker.interrupt();
            }
        }
    }

    public boolean isAlive(Set<CraftWorker> workers, int taskId) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                if (worker.getTaskId() == taskId) {
                    return worker.isAlive();
                }
            }
            return false;
        }
    }

    public void removeWorker(Set<CraftWorker> workers, CraftWorker worker) {
        synchronized (workers) {
            workers.remove(worker);
        }
    }
}

