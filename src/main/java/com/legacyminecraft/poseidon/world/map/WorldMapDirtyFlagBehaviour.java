package com.legacyminecraft.poseidon.world.map;

public final class WorldMapDirtyFlagBehaviour {
    private static final WorldMapDirtyFlagBehaviour INSTANCE = new WorldMapDirtyFlagBehaviour();

    private WorldMapDirtyFlagBehaviour() {
    }

    public static WorldMapDirtyFlagBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean markDirty() {
        return true;
    }

    public boolean setDirty(boolean flag) {
        return flag;
    }

    public boolean isDirty(boolean dirty) {
        return dirty;
    }
}
