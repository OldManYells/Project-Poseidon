package com.legacyminecraft.poseidon.inventory;


public final class FurnaceResultSlotBehaviour {
    private static final FurnaceResultSlotBehaviour INSTANCE = new FurnaceResultSlotBehaviour();

    private FurnaceResultSlotBehaviour() {
    }

    public static FurnaceResultSlotBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isAllowed(ItemStack itemstack) {
        return false;
    }

    public void onSmelted(ItemStack itemstack, EntityHuman human) {
        // Deferred: achievement/stat wiring is still owned by legacy compatibility path.
    }
}
