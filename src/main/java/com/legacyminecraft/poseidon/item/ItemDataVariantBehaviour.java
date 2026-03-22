package com.legacyminecraft.poseidon.item;

public final class ItemDataVariantBehaviour {
    private static final ItemDataVariantBehaviour INSTANCE = new ItemDataVariantBehaviour();

    private ItemDataVariantBehaviour() {
    }

    public static ItemDataVariantBehaviour getInstance() {
        return INSTANCE;
    }

    public int identity(int data) {
        return data;
    }

    public int leavesPlacementData(int data) {
        return data | 8;
    }

    public int pistonPlacementData() {
        return 7;
    }
}
