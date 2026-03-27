package org.bukkit.craftbukkit.scheduler;

import com.legacyminecraft.compat.bukkit.SchedulerObjectContainerBehaviour;

import java.util.concurrent.atomic.AtomicReference;

public class ObjectContainer<T> {

    private final SchedulerObjectContainerBehaviour schedulerObjectContainerBehaviour =
            SchedulerObjectContainerBehaviour.getInstance();
    private final AtomicReference<T> objectReference = new AtomicReference<T>();

    public void setObject(T object) {
        schedulerObjectContainerBehaviour.setObject(objectReference, object);
    }

    public T getObject() {
        return schedulerObjectContainerBehaviour.getObject(objectReference);
    }

}
