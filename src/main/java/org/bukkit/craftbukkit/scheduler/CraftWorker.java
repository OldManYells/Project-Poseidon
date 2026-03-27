package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.compat.bukkit.SchedulerWorkerIdentityBehaviour;
import com.legacyminecraft.compat.bukkit.SchedulerWorkerLifecycleBehaviour;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitWorker;

public class CraftWorker implements Runnable, BukkitWorker {

    private static final SchedulerWorkerIdentityBehaviour schedulerWorkerIdentityBehaviour =
            SchedulerWorkerIdentityBehaviour.getInstance();
    private static final SchedulerWorkerLifecycleBehaviour schedulerWorkerLifecycleBehaviour =
            SchedulerWorkerLifecycleBehaviour.getInstance();

    private final int hashId;

    private final Plugin owner;
    private final int taskId;

    private final Thread t;
    private final CraftThreadManager parent;

    private final Runnable task;

    CraftWorker(CraftThreadManager parent, Runnable task, Plugin owner, int taskId) {
        this.parent = parent;
        this.taskId = taskId;
        this.task = task;
        this.owner = owner;
        this.hashId = CraftWorker.getNextHashId();
        t = schedulerWorkerLifecycleBehaviour.startWorkerThread(this);
    }

    public void run() {
        schedulerWorkerLifecycleBehaviour.executeWithCleanup(task, new Runnable() {
            public void run() {
                parent.removeWorker(CraftWorker.this);
            }
        });
    }

    public int getTaskId() {
        return taskId;
    }

    public Plugin getOwner() {
        return owner;
    }

    public Thread getThread() {
        return t;
    }

    public void interrupt() {
        schedulerWorkerLifecycleBehaviour.interrupt(t);
    }

    public boolean isAlive() {
        return schedulerWorkerLifecycleBehaviour.isAlive(t);
    }

    private static int getNextHashId() {
        return schedulerWorkerIdentityBehaviour.nextWorkerHashId();
    }

    @Override
    public int hashCode() {
        return schedulerWorkerIdentityBehaviour.hashCodeForHashId(hashId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!(other instanceof CraftWorker)) {
            return false;
        }

        CraftWorker otherCraftWorker = (CraftWorker) other;
        return schedulerWorkerIdentityBehaviour.sameHashId(hashId, otherCraftWorker.hashCode());
    }

}
