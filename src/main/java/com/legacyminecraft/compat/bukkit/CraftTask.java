package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat scheduler task scaffold.
 */
public class CraftTask implements BukkitTask {
    private final int taskId;
    private final Plugin owner;
    private final Runnable runnable;

    public CraftTask(Plugin owner, Runnable runnable) {
        this(0, owner, runnable);
    }

    public CraftTask(int taskId, Plugin owner, Runnable runnable) {
        this.taskId = taskId;
        this.owner = owner;
        this.runnable = runnable;
    }

    public int getTaskId() {
        return taskId;
    }

    public Plugin getOwner() {
        return owner;
    }

    public Runnable getRunnable() {
        return runnable;
    }
}
