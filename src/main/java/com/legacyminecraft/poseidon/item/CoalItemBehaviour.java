package com.legacyminecraft.poseidon.item;

public final class CoalItemBehaviour {
    private static final CoalItemBehaviour INSTANCE = new CoalItemBehaviour();

    private CoalItemBehaviour() {
    }

    public static CoalItemBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasSubtypes() {
        return true;
    }

    public int defaultDataValue() {
        return 0;
    }
}
