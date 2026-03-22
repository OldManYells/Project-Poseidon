package com.legacyminecraft.poseidon.item;

public final class SignItemPlacementBehaviour {
    private static final SignItemPlacementBehaviour INSTANCE = new SignItemPlacementBehaviour();

    private SignItemPlacementBehaviour() {
    }

    public static SignItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBottomFace(int face) {
        return face == 0;
    }

    public boolean canAttachToClickedBlock(boolean clickedBuildable) {
        return clickedBuildable;
    }

    public ItemPlacementMathBehaviour.Position resolveTarget(int x, int y, int z, int face, ItemPlacementMathBehaviour math) {
        return math.applyFaceOffset(x, y, z, face);
    }

    public boolean isStandingSign(int face) {
        return face == 1;
    }

    public int resolvePlacedBlockId(int face, int signPostId, int wallSignId) {
        return isStandingSign(face) ? signPostId : wallSignId;
    }

    public int resolvePlacedData(int face, float yaw, ItemPlacementMathBehaviour math) {
        if (isStandingSign(face)) {
            return math.yawToSignRotation16(yaw);
        }
        return face;
    }
}
