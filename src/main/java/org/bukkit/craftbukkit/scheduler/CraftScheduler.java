package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.compat.bukkit.CraftSchedulerMainThreadHeartbeatSystem;
import com.legacyminecraft.compat.bukkit.CraftSchedulerRunLoopSystem;
import com.legacyminecraft.compat.bukkit.SchedulerTaskCancellationBehaviour;
import com.legacyminecraft.compat.bukkit.SchedulerTaskInspectionBehaviour;
import com.legacyminecraft.poseidon.utility.PerformanceStatistic;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class CraftScheduler implements BukkitScheduler, Runnable {

    private static final Logger logger = Logger.getLogger("Minecraft");
    private static final CraftSchedulerRunLoopSystem craftSchedulerRunLoopSystem =
            CraftSchedulerRunLoopSystem.getInstance();
    private static final CraftSchedulerMainThreadHeartbeatSystem craftSchedulerMainThreadHeartbeatSystem =
            CraftSchedulerMainThreadHeartbeatSystem.getInstance();
    private static final SchedulerTaskCancellationBehaviour schedulerTaskCancellationBehaviour =
            SchedulerTaskCancellationBehaviour.getInstance();
    private static final SchedulerTaskInspectionBehaviour schedulerTaskInspectionBehaviour =
            SchedulerTaskInspectionBehaviour.getInstance();

    private final CraftServer server;

    private final CraftThreadManager craftThreadManager = new CraftThreadManager();

    private final LinkedList<CraftTask> mainThreadQueue = new LinkedList<CraftTask>();
    private final LinkedList<CraftTask> syncedTasks = new LinkedList<CraftTask>();

    private final TreeMap<CraftTask, Boolean> schedulerQueue = new TreeMap<CraftTask, Boolean>();

    private final Object currentTickSync = new Object();
    private Long currentTick = 0L;

    // This lock locks the mainThreadQueue and the currentTick value
    private final Lock mainThreadLock = new ReentrantLock();
    private final Lock syncedTasksLock = new ReentrantLock();

    public void run() {
        craftSchedulerRunLoopSystem.runLoop(new CraftSchedulerRunLoopSystem.SchedulerRunLoopAccess() {
            public Object getSchedulerQueueMonitor() {
                return schedulerQueue;
            }

            public boolean isSchedulerQueueEmpty() {
                return schedulerQueue.isEmpty();
            }

            public CraftTask getFirstScheduledTask() {
                return schedulerQueue.firstKey();
            }

            public long getCurrentTick() {
                return CraftScheduler.this.getCurrentTick();
            }

            public long getExecutionTick(CraftTask task) {
                return task.getExecutionTick();
            }

            public long getPeriod(CraftTask task) {
                return task.getPeriod();
            }

            public void processTask(CraftTask task) {
                CraftScheduler.this.processTask(task);
            }

            public void removeScheduledTask(CraftTask task) {
                schedulerQueue.remove(task);
            }

            public void updateExecution(CraftTask task) {
                task.updateExecution();
            }

            public void enqueueScheduledTask(CraftTask task) {
                schedulerQueue.put(task, task.isSync());
            }
        });
    }

    void processTask(CraftTask task) {
        if (task.isSync()) {
            addToMainThreadQueue(task);
        } else {
            craftThreadManager.executeTask(task.getTask(), task.getOwner(), task.getIdNumber());
        }
    }

    public CraftScheduler(CraftServer server) {
        this.server = server;

        Thread t = new Thread(this);
        t.start();

        // Project Poseidon - Start - Synchronous task performance reporting
        this.taskPerformanceEnabled = Poseidon.getServer().getConfig().getConfigBoolean("settings.performance-monitoring.task-reporting.enabled");
        this.printOnSlowTask = Poseidon.getServer().getConfig().getConfigBoolean("settings.performance-monitoring.task-reporting.print-on-slow-tasks.enabled");
        this.printOnSlowTaskThreshold = Poseidon.getServer().getConfig().getConfigInteger("settings.performance-monitoring.task-reporting.print-on-slow-tasks.value");

        this.taskPerformance = Poseidon.getServer().getTaskPerformance();
        // Project Poseidon - End - Synchronous task performance reporting
    }

    // Project Poseidon - Start - Synchronous task performance reporting
    private final boolean taskPerformanceEnabled; // Project Poseidon
    private final Map<String, PerformanceStatistic> taskPerformance; // Project Poseidon

    private final boolean printOnSlowTask;
    private final int printOnSlowTaskThreshold;

    // Project Poseidon - End - Synchronous task performance reporting


    public void mainThreadHeartbeat(long currentTick) {
        craftSchedulerMainThreadHeartbeatSystem.runHeartbeat(new MainThreadHeartbeatAccessAdapter(), currentTick);
    }

    private final class MainThreadHeartbeatAccessAdapter implements CraftSchedulerMainThreadHeartbeatSystem.MainThreadHeartbeatAccess {
        public boolean tryLockSyncedTasks() {
            return syncedTasksLock.tryLock();
        }

        public void unlockSyncedTasks() {
            syncedTasksLock.unlock();
        }

        public boolean tryLockMainThread() {
            return mainThreadLock.tryLock();
        }

        public void unlockMainThread() {
            mainThreadLock.unlock();
        }

        public void setCurrentTick(long currentTick) {
            CraftScheduler.this.currentTick = currentTick;
        }

        public void transferMainThreadQueueToSyncedTasks() {
            while (!mainThreadQueue.isEmpty()) {
                syncedTasks.addLast(mainThreadQueue.removeFirst());
            }
        }

        public boolean isSyncedTasksEmpty() {
            return syncedTasks.isEmpty();
        }

        public CraftTask removeFirstSyncedTask() {
            return syncedTasks.removeFirst();
        }

        public void runSyncedTask(CraftTask task) {
            task.getTask().run();
        }

        public void removeScheduledTask(CraftTask task) {
            synchronized (schedulerQueue) {
                schedulerQueue.remove(task);
            }
        }

        public boolean isTaskPerformanceEnabled() {
            return taskPerformanceEnabled;
        }

        public Map<String, PerformanceStatistic> getTaskPerformance() {
            return taskPerformance;
        }

        public boolean isPrintOnSlowTaskEnabled() {
            return printOnSlowTask;
        }

        public int getPrintOnSlowTaskThreshold() {
            return printOnSlowTaskThreshold;
        }

        public Logger getTaskLogger() {
            return logger;
        }

        public Logger getServerLogger() {
            return server.getLogger();
        }
    }

    long getCurrentTick() {
        mainThreadLock.lock();
        long tempTick = 0;
        try {
            tempTick = currentTick;
        } finally {
            mainThreadLock.unlock();
        }
        return tempTick;
    }

    void addToMainThreadQueue(CraftTask task) {
        mainThreadLock.lock();
        try {
            mainThreadQueue.addLast(task);
        } finally {
            mainThreadLock.unlock();
        }
    }

    void wipeSyncedTasks() {
        syncedTasksLock.lock();
        try {
            syncedTasks.clear();
        } finally {
            syncedTasksLock.unlock();
        }
    }

    void wipeMainThreadQueue() {
        mainThreadLock.lock();
        try {
            mainThreadQueue.clear();
        } finally {
            mainThreadLock.unlock();
        }
    }

    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return scheduleSyncRepeatingTask(plugin, task, delay, -1);
    }

    public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
        return scheduleSyncDelayedTask(plugin, task, 0L);
    }

    public int scheduleSyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (delay < 0) {
            throw new IllegalArgumentException("Delay cannot be less than 0");
        }

        CraftTask newTask = new CraftTask(plugin, task, true, getCurrentTick() + delay, period);

        synchronized (schedulerQueue) {
            schedulerQueue.put(newTask, true);
            schedulerQueue.notify();
        }
        return newTask.getIdNumber();
    }

    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
        return scheduleAsyncRepeatingTask(plugin, task, delay, -1);
    }

    public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
        return scheduleAsyncDelayedTask(plugin, task, 0L);
    }

    public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable task, long delay, long period) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        if (delay < 0) {
            throw new IllegalArgumentException("Delay cannot be less than 0");
        }

        CraftTask newTask = new CraftTask(plugin, task, false, getCurrentTick() + delay, period);

        synchronized (schedulerQueue) {
            schedulerQueue.put(newTask, false);
            schedulerQueue.notify();
        }
        return newTask.getIdNumber();
    }

    public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
        CraftFuture<T> craftFuture = new CraftFuture<T>(this, task);
        synchronized (craftFuture) {
            int taskId = scheduleSyncDelayedTask(plugin, craftFuture);
            craftFuture.setTaskId(taskId);
        }
        return craftFuture;
    }

    public void cancelTask(int taskId) {
        schedulerTaskCancellationBehaviour.cancelTask(
                schedulerQueue,
                mainThreadQueue,
                syncedTasks,
                mainThreadLock,
                syncedTasksLock,
                craftThreadManager.workers,
                taskId
        );
    }

    public void cancelTasks(Plugin plugin) {
        schedulerTaskCancellationBehaviour.cancelTasks(
                schedulerQueue,
                mainThreadQueue,
                syncedTasks,
                mainThreadLock,
                syncedTasksLock,
                craftThreadManager.workers,
                plugin
        );
    }

    public void cancelAllTasks() {
        schedulerTaskCancellationBehaviour.cancelAllTasks(
                schedulerQueue,
                mainThreadQueue,
                syncedTasks,
                mainThreadLock,
                syncedTasksLock,
                craftThreadManager.workers
        );
    }

    public boolean isCurrentlyRunning(int taskId) {
        return schedulerTaskInspectionBehaviour.isCurrentlyRunning(craftThreadManager.workers, taskId);
    }

    public boolean isQueued(int taskId) {
        return schedulerTaskInspectionBehaviour.isQueued(schedulerQueue, taskId);
    }

    public List<BukkitWorker> getActiveWorkers() {
        return schedulerTaskInspectionBehaviour.getActiveWorkers(craftThreadManager.workers);
    }

    public List<BukkitTask> getPendingTasks() {
        return schedulerTaskInspectionBehaviour.getPendingTasks(
                mainThreadQueue,
                syncedTasks,
                schedulerQueue,
                mainThreadLock,
                syncedTasksLock
        );
    }

}
