package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.WatchableObject;

public final class WatchableObjectStateBehaviour {
    private static final WatchableObjectStateBehaviour INSTANCE = new WatchableObjectStateBehaviour();

    private WatchableObjectStateBehaviour() {
    }

    public static WatchableObjectStateBehaviour getInstance() {
        return INSTANCE;
    }

    public static final class InitState {
        public final int typeId;
        public final int objectId;
        public final Object value;
        public final boolean dirty;

        public InitState(int typeId, int objectId, Object value, boolean dirty) {
            this.typeId = typeId;
            this.objectId = objectId;
            this.value = value;
            this.dirty = dirty;
        }
    }

    public InitState initialize(int typeId, int objectId, Object value) {
        return new InitState(typeId, objectId, value, true);
    }

    public int getObjectId(WatchableObject source) {
        return source.poseidonGetObjectId();
    }

    public void setValue(WatchableObject target, Object value) {
        target.poseidonSetValue(value);
    }

    public Object getValue(WatchableObject source) {
        return source.poseidonGetValue();
    }

    public int getTypeId(WatchableObject source) {
        return source.poseidonGetTypeId();
    }

    public boolean isDirty(WatchableObject source) {
        return source.poseidonIsDirty();
    }

    public void setDirty(WatchableObject target, boolean dirty) {
        target.poseidonSetDirty(dirty);
    }
}
