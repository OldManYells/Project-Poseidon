package com.legacyminecraft.poseidon.item;

public final class DoorItemPlacementBehaviour {
    private static final DoorItemPlacementBehaviour INSTANCE = new DoorItemPlacementBehaviour();

    private DoorItemPlacementBehaviour() {
    }

    public static DoorItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveDoorBlockId(boolean isWoodDoorMaterial, int woodenDoorId, int ironDoorId) {
        return isWoodDoorMaterial ? woodenDoorId : ironDoorId;
    }

    public int resolveFacingFromYaw(float yaw, ItemPlacementMathBehaviour math) {
        return math.yawToCardinal4ForDoor(yaw);
    }

    public ItemPlacementMathBehaviour.Offset resolveOffset(int facing, ItemPlacementMathBehaviour math) {
        return math.facingToOffset(facing);
    }

    public int countSupport(boolean lowerSolid, boolean upperSolid) {
        return (lowerSolid ? 1 : 0) + (upperSolid ? 1 : 0);
    }

    public boolean hasDoorHalf(boolean lowerSameDoor, boolean upperSameDoor) {
        return lowerSameDoor || upperSameDoor;
    }

    public boolean shouldMirrorHinge(boolean leftHasDoor, boolean rightHasDoor, int leftSupport, int rightSupport) {
        if (leftHasDoor && !rightHasDoor) {
            return true;
        }
        return rightSupport > leftSupport;
    }

    public int resolveBottomData(int facing, boolean mirrorHinge) {
        if (!mirrorHinge) {
            return facing;
        }
        int data = (facing - 1) & 3;
        return data + 4;
    }

    public int resolveTopData(int bottomData) {
        return bottomData + 8;
    }
}
