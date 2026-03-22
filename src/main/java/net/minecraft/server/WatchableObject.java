package net.minecraft.server;

import com.legacyminecraft.poseidon.entity.WatchableObjectStateBehaviour;

public class WatchableObject {
    private static final WatchableObjectStateBehaviour WATCHABLE_OBJECT_STATE_BEHAVIOUR = WatchableObjectStateBehaviour.getInstance();

    private final int a;
    private final int b;
    private Object c;
    private boolean d;

    public WatchableObject(int i, int j, Object object) {
        WatchableObjectStateBehaviour.InitState init = WATCHABLE_OBJECT_STATE_BEHAVIOUR.initialize(i, j, object);
        this.b = init.objectId;
        this.c = init.value;
        this.a = init.typeId;
        this.d = init.dirty;
    }

    public int a() {
        return WATCHABLE_OBJECT_STATE_BEHAVIOUR.getObjectId(this);
    }

    public void a(Object object) {
        WATCHABLE_OBJECT_STATE_BEHAVIOUR.setValue(this, object);
    }

    public Object b() {
        return WATCHABLE_OBJECT_STATE_BEHAVIOUR.getValue(this);
    }

    public int c() {
        return WATCHABLE_OBJECT_STATE_BEHAVIOUR.getTypeId(this);
    }

    public boolean d() {
        return WATCHABLE_OBJECT_STATE_BEHAVIOUR.isDirty(this);
    }

    public void a(boolean flag) {
        WATCHABLE_OBJECT_STATE_BEHAVIOUR.setDirty(this, flag);
    }

    public final int poseidonGetObjectId() {
        return this.b;
    }

    public final void poseidonSetValue(Object value) {
        this.c = value;
    }

    public final Object poseidonGetValue() {
        return this.c;
    }

    public final int poseidonGetTypeId() {
        return this.a;
    }

    public final boolean poseidonIsDirty() {
        return this.d;
    }

    public final void poseidonSetDirty(boolean dirty) {
        this.d = dirty;
    }
}
