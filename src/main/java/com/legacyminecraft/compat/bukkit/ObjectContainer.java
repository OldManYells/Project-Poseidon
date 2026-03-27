package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat object container scaffold.
 */
public class ObjectContainer<T> {
    private T object;

    public ObjectContainer() {
    }

    public ObjectContainer(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
