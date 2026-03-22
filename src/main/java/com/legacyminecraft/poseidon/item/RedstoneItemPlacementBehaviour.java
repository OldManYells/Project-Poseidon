package com.legacyminecraft.poseidon.item;

public final class RedstoneItemPlacementBehaviour {
    private static final RedstoneItemPlacementBehaviour INSTANCE = new RedstoneItemPlacementBehaviour();

    private RedstoneItemPlacementBehaviour() {
    }

    public static RedstoneItemPlacementBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemPlacementMathBehaviour.Position resolveTarget(int x, int y, int z, int face, int clickedTypeId, int snowId, ItemPlacementMathBehaviour math) {
        if (math.isSnow(clickedTypeId, snowId)) {
            return new ItemPlacementMathBehaviour.Position(x, y, z);
        }
        return math.applyFaceOffset(x, y, z, face);
    }

    public boolean requiresEmptyTarget(int clickedTypeId, int snowId, ItemPlacementMathBehaviour math) {
        return !math.isSnow(clickedTypeId, snowId);
    }
}
