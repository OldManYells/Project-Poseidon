package com.legacyminecraft.compat.bukkit;


import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * Canonical behavior for CraftBukkit scheduler task cancellation flows.
 */
public final class SchedulerTaskCancellationBehaviour {
    private static final SchedulerTaskCancellationBehaviour INSTANCE = new SchedulerTaskCancellationBehaviour();

    private SchedulerTaskCancellationBehaviour() {
    }

    public static SchedulerTaskCancellationBehaviour getInstance() {
        return INSTANCE;
    }

    public void cancelTask(
            Map<CraftTask, Boolean> schedulerQueue,
            Collection<CraftTask> mainThreadQueue,
            Collection<CraftTask> syncedTasks,
            Lock mainThreadLock,
            Lock syncedTasksLock,
            Set<CraftWorker> workers,
            int taskId
    ) {
        removeMatchingTasks(
                schedulerQueue,
                mainThreadQueue,
                syncedTasks,
                mainThreadLock,
                syncedTasksLock,
                new TaskMatcher() {
                    public boolean matches(CraftTask task) {
                        return task.getTaskId() == taskId;
                    }
                }
        );

        interruptMatchingWorkers(workers, new WorkerMatcher() {
            public boolean matches(CraftWorker worker) {
                return worker.getTaskId() == taskId;
            }
        });
    }

    public void cancelTasks(
            Map<CraftTask, Boolean> schedulerQueue,
            Collection<CraftTask> mainThreadQueue,
            Collection<CraftTask> syncedTasks,
            Lock mainThreadLock,
            Lock syncedTasksLock,
            Set<CraftWorker> workers,
            final Plugin plugin
    ) {
        removeMatchingTasks(
                schedulerQueue,
                mainThreadQueue,
                syncedTasks,
                mainThreadLock,
                syncedTasksLock,
                new TaskMatcher() {
                    public boolean matches(CraftTask task) {
                        return task.getOwner().equals(plugin);
                    }
                }
        );

        interruptMatchingWorkers(workers, new WorkerMatcher() {
            public boolean matches(CraftWorker worker) {
                return worker.getOwner().equals(plugin);
            }
        });
    }

    public void cancelAllTasks(
            Map<CraftTask, Boolean> schedulerQueue,
            Collection<CraftTask> mainThreadQueue,
            Collection<CraftTask> syncedTasks,
            Lock mainThreadLock,
            Lock syncedTasksLock,
            Set<CraftWorker> workers
    ) {
        syncedTasksLock.lock();
        try {
            synchronized (schedulerQueue) {
                mainThreadLock.lock();
                try {
                    schedulerQueue.clear();
                    mainThreadQueue.clear();
                    syncedTasks.clear();
                } finally {
                    mainThreadLock.unlock();
                }
            }
        } finally {
            syncedTasksLock.unlock();
        }

        interruptMatchingWorkers(workers, new WorkerMatcher() {
            public boolean matches(CraftWorker worker) {
                return true;
            }
        });
    }

    private void removeMatchingTasks(
            Map<CraftTask, Boolean> schedulerQueue,
            Collection<CraftTask> mainThreadQueue,
            Collection<CraftTask> syncedTasks,
            Lock mainThreadLock,
            Lock syncedTasksLock,
            TaskMatcher matcher
    ) {
        syncedTasksLock.lock();
        try {
            synchronized (schedulerQueue) {
                mainThreadLock.lock();
                try {
                    removeMatchingFromQueue(schedulerQueue.keySet().iterator(), matcher);
                    removeMatchingFromQueue(mainThreadQueue.iterator(), matcher);
                    removeMatchingFromQueue(syncedTasks.iterator(), matcher);
                } finally {
                    mainThreadLock.unlock();
                }
            }
        } finally {
            syncedTasksLock.unlock();
        }
    }

    private void removeMatchingFromQueue(Iterator<CraftTask> iterator, TaskMatcher matcher) {
        while (iterator.hasNext()) {
            CraftTask current = iterator.next();
            if (matcher.matches(current)) {
                iterator.remove();
            }
        }
    }

    private void interruptMatchingWorkers(Set<CraftWorker> workers, WorkerMatcher matcher) {
        synchronized (workers) {
            for (CraftWorker worker : workers) {
                if (matcher.matches(worker)) {
                    worker.interrupt();
                }
            }
        }
    }

    private interface TaskMatcher {
        boolean matches(CraftTask task);
    }

    private interface WorkerMatcher {
        boolean matches(CraftWorker worker);
    }
}
