package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement/survival rules for legacy flower wrappers.
 */
public final class FlowerStateBehaviour {
    private static final FlowerStateBehaviour INSTANCE = new FlowerStateBehaviour();

    private FlowerStateBehaviour() {
    }

    public static FlowerStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlantOn(int belowTypeId, int grassBlockId, int dirtBlockId, int farmlandBlockId) {
        return belowTypeId == grassBlockId || belowTypeId == dirtBlockId || belowTypeId == farmlandBlockId;
    }

    public boolean canPlace(boolean superCanPlace, boolean validSupport) {
        return superCanPlace && validSupport;
    }

    public boolean canStay(int localLightLevel, boolean hasSkyOrLoadedState, boolean validSupport) {
        return (localLightLevel >= 8 || hasSkyOrLoadedState) && validSupport;
    }

    public boolean shouldDropForInvalidPlacement(boolean canStay) {
        return !canStay;
    }
}
