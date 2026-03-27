package com.legacyminecraft.compat.bukkit;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Canonical behavior for CraftBukkit scheduler object container state access.
 */
public final class SchedulerObjectContainerBehaviour {
    private static final SchedulerObjectContainerBehaviour INSTANCE = new SchedulerObjectContainerBehaviour();

    private SchedulerObjectContainerBehaviour() {
    }

    public static SchedulerObjectContainerBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> void setObject(AtomicReference<T> objectReference, T object) {
        objectReference.set(object);
    }

    public <T> T getObject(AtomicReference<T> objectReference) {
        return objectReference.get();
    }
}

