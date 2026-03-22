package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.poseidon.compat.bukkit.SchedulerTaskIdentityBehaviour;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class CraftTask implements Comparable<Object>, BukkitTask {

    private final Runnable task;
    private final boolean syncTask;
    private long executionTick;
    private final long period;
    private final Plugin owner;
    private final int idNumber;

    private static final SchedulerTaskIdentityBehaviour schedulerTaskIdentityBehaviour =
            SchedulerTaskIdentityBehaviour.getInstance();

    CraftTask(Plugin owner, Runnable task, boolean syncTask) {
        this(owner, task, syncTask, -1, -1);
    }

    CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick) {
        this(owner, task, syncTask, executionTick, -1);
    }

    CraftTask(Plugin owner, Runnable task, boolean syncTask, long executionTick, long period) {
        this.task = task;
        this.syncTask = syncTask;
        this.executionTick = executionTick;
        this.period = period;
        this.owner = owner;
        this.idNumber = CraftTask.getNextId();
    }

    static int getNextId() {
        return schedulerTaskIdentityBehaviour.nextTaskId();
    }

    Runnable getTask() {
        return task;
    }

    public boolean isSync() {
        return syncTask;
    }

    long getExecutionTick() {
        return executionTick;
    }

    long getPeriod() {
        return period;
    }

    public Plugin getOwner() {
        return owner;
    }

    void updateExecution() {
        executionTick += period;
    }

    public int getTaskId() {
        return getIdNumber();
    }

    int getIdNumber() {
        return idNumber;
    }

    public int compareTo(Object other) {
        if (!(other instanceof CraftTask)) {
            return 0;
        }

        CraftTask otherTask = (CraftTask) other;
        return schedulerTaskIdentityBehaviour.compareByExecutionThenId(
                executionTick,
                getIdNumber(),
                otherTask.getExecutionTick(),
                otherTask.getIdNumber()
        );
    }

    @Override
    public boolean equals(Object other) {

        if (other == null) {
            return false;
        }

        if (!(other instanceof CraftTask)) {
            return false;
        }

        CraftTask otherCraftTask = (CraftTask) other;
        return schedulerTaskIdentityBehaviour.sameTaskId(getIdNumber(), otherCraftTask.getIdNumber());
    }

    @Override
    public int hashCode() {
        return schedulerTaskIdentityBehaviour.hashCodeForTaskId(getIdNumber());
    }
}
