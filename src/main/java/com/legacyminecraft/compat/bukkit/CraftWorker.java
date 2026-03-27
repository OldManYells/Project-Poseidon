package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat scheduler worker scaffold.
 */
public class CraftWorker implements BukkitWorker {
    private final Plugin owner;
    private final int taskId;
    private final Thread thread;

    public CraftWorker(Plugin owner, int taskId) {
        this(owner, taskId, new Thread());
    }

    public CraftWorker(Plugin owner, int taskId, Thread thread) {
        this.owner = owner;
        this.taskId = taskId;
        this.thread = thread == null ? new Thread() : thread;
    }

    @Override
    public Plugin getOwner() {
        return owner;
    }

    @Override
    public int getTaskId() {
        return taskId;
    }

    @Override
    public boolean isAlive() {
        return thread.isAlive();
    }

    @Override
    public void interrupt() {
        thread.interrupt();
    }
}
