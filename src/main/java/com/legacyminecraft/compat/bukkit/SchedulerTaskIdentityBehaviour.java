package com.legacyminecraft.compat.bukkit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Canonical behavior for CraftBukkit scheduler task identity and ordering rules.
 */
public final class SchedulerTaskIdentityBehaviour {
    private static final SchedulerTaskIdentityBehaviour INSTANCE = new SchedulerTaskIdentityBehaviour();

    private final AtomicInteger taskIdCounter = new AtomicInteger(1);

    private SchedulerTaskIdentityBehaviour() {
    }

    public static SchedulerTaskIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public int nextTaskId() {
        return taskIdCounter.incrementAndGet();
    }

    public int compareByExecutionThenId(long executionTick, int taskId, long otherExecutionTick, int otherTaskId) {
        long executionTickDifference = executionTick - otherExecutionTick;
        if (executionTickDifference > 0L) {
            return 1;
        }
        if (executionTickDifference < 0L) {
            return -1;
        }
        return taskId - otherTaskId;
    }

    public boolean sameTaskId(int taskId, int otherTaskId) {
        return taskId == otherTaskId;
    }

    public int hashCodeForTaskId(int taskId) {
        return taskId;
    }
}

