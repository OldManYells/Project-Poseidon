package com.legacyminecraft.poseidon.entity;

/**
 * Canonical watchable-object scaffold for DataWatcher codec behaviour.
 */
public class WatchableObject {
    private final int typeId;
    private final int objectId;
    private Object value;
    private boolean dirty;

    public WatchableObject(int typeId, int objectId, Object value) {
        this.typeId = typeId;
        this.objectId = objectId;
        this.value = value;
        this.dirty = true;
    }

    public int a() {
        return objectId;
    }

    public void a(Object value) {
        this.value = value;
    }

    public Object b() {
        return value;
    }

    public int c() {
        return typeId;
    }

    public boolean d() {
        return dirty;
    }

    public void a(boolean dirty) {
        this.dirty = dirty;
    }
}
