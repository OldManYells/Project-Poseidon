package com.legacyminecraft.poseidon.item;

public final class BedItemPlacementBehaviour {
    private static final BedItemPlacementBehaviour INSTANCE = new BedItemPlacementBehaviour();

    private BedItemPlacementBehaviour() {
    }

    public static BedItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveFacingFromYaw(float yaw, ItemPlacementMathBehaviour math) {
        return math.yawToCardinal4(yaw);
    }

    public ItemPlacementMathBehaviour.Offset resolveOffset(int facing, ItemPlacementMathBehaviour math) {
        return math.facingToOffset(facing);
    }

    public boolean canPlaceBed(boolean footEmpty, boolean headEmpty, boolean footSupported, boolean headSupported) {
        return footEmpty && headEmpty && footSupported && headSupported;
    }

    public int resolveHeadPartData(int footData) {
        return footData + 8;
    }
}
