package com.legacyminecraft.poseidon.compat.bukkit;

/**
 * Canonical behavior for CraftBukkit scheduler worker thread lifecycle flow.
 */
public final class SchedulerWorkerLifecycleBehaviour {
    private static final SchedulerWorkerLifecycleBehaviour INSTANCE = new SchedulerWorkerLifecycleBehaviour();

    private SchedulerWorkerLifecycleBehaviour() {
    }

    public static SchedulerWorkerLifecycleBehaviour getInstance() {
        return INSTANCE;
    }

    public Thread startWorkerThread(Runnable workerRunnable) {
        Thread workerThread = new Thread(workerRunnable);
        workerThread.start();
        return workerThread;
    }

    public void executeWithCleanup(Runnable task, Runnable cleanupAction) {
        try {
            task.run();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            cleanupAction.run();
        }
    }

    public void interrupt(Thread workerThread) {
        workerThread.interrupt();
    }

    public boolean isAlive(Thread workerThread) {
        return workerThread.isAlive();
    }
}

