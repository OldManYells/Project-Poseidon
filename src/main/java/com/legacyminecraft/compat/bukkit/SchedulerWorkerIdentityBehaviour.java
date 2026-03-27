package com.legacyminecraft.compat.bukkit;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Canonical behavior for CraftBukkit scheduler worker identity semantics.
 */
public final class SchedulerWorkerIdentityBehaviour {
    private static final SchedulerWorkerIdentityBehaviour INSTANCE = new SchedulerWorkerIdentityBehaviour();

    private final AtomicInteger workerHashIdCounter = new AtomicInteger(1);

    private SchedulerWorkerIdentityBehaviour() {
    }

    public static SchedulerWorkerIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public int nextWorkerHashId() {
        return workerHashIdCounter.getAndIncrement();
    }

    public boolean sameHashId(int hashId, int otherHashId) {
        return hashId == otherHashId;
    }

    public int hashCodeForHashId(int hashId) {
        return hashId;
    }
}

