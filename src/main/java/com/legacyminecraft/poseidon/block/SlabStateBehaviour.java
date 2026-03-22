package com.legacyminecraft.poseidon.block;

/**
 * Canonical texture/merge/drop policy for legacy slab wrappers.
 */
public final class SlabStateBehaviour {
    private static final SlabStateBehaviour INSTANCE = new SlabStateBehaviour();

    private SlabStateBehaviour() {
    }

    public static SlabStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySideAndVariant(int side, int variantData) {
        return variantData == 0
                ? (side <= 1 ? 6 : 5)
                : (variantData == 1
                ? (side == 0 ? 208 : (side == 1 ? 176 : 192))
                : (variantData == 2 ? 4 : (variantData == 3 ? 16 : 6)));
    }

    public boolean shouldMergeWithBelow(int belowTypeId, int currentData, int belowData, int stepBlockId) {
        return belowTypeId == stepBlockId && currentData == belowData;
    }

    public int resolveDroppedItemId(int stepBlockId) {
        return stepBlockId;
    }

    public int resolveDropCount(boolean doubleSlab) {
        return doubleSlab ? 2 : 1;
    }
}
