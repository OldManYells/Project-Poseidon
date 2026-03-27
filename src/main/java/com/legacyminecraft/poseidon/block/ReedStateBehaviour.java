package com.legacyminecraft.poseidon.block;


/**
 * Canonical placement and drop policy for legacy reed wrappers.
 */
public final class ReedStateBehaviour {
    private static final ReedStateBehaviour INSTANCE = new ReedStateBehaviour();

    private ReedStateBehaviour() {
    }

    public static ReedStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canRemainPlaced(
            int belowTypeId,
            int reedBlockId,
            int grassBlockId,
            int dirtBlockId,
            Material westMaterial,
            Material eastMaterial,
            Material northMaterial,
            Material southMaterial,
            Material waterMaterial
    ) {
        if (belowTypeId == reedBlockId) {
            return true;
        }

        if (belowTypeId != grassBlockId && belowTypeId != dirtBlockId) {
            return false;
        }

        return westMaterial == waterMaterial
                || eastMaterial == waterMaterial
                || northMaterial == waterMaterial
                || southMaterial == waterMaterial;
    }

    public int dropItemId(int sugarCaneItemId) {
        return sugarCaneItemId;
    }
}
