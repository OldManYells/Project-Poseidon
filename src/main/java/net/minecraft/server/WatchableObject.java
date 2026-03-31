package net.minecraft.server;

public class WatchableObject {

    private final int objectType;
    private final int dataValueId;
    private Object value;
    private boolean watched;

    public WatchableObject(int objectType, int dataValueId, Object value) {
        this.dataValueId = dataValueId;
        this.value = value;
        this.objectType = objectType;
        this.watched = true;
    }

    public int getDataValueId() {
        return this.dataValueId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return this.value;
    }

    public int getObjectType() {
        return this.objectType;
    }

    public boolean isWatched() {
        return this.watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    @Deprecated
    public int a() {
        return this.getDataValueId();
    }

    @Deprecated
    public void a(Object value) {
        this.setValue(value);
    }

    @Deprecated
    public Object b() {
        return this.getValue();
    }

    @Deprecated
    public int c() {
        return this.getObjectType();
    }

    @Deprecated
    public boolean d() {
        return this.isWatched();
    }

    @Deprecated
    public void a(boolean watched) {
        this.setWatched(watched);
    }
}
