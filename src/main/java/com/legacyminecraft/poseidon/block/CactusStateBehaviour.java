package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.AxisAlignedBB;

/**
 * Canonical placement and shape policy for legacy cactus wrappers.
 */
public final class CactusStateBehaviour {
    private static final CactusStateBehaviour INSTANCE = new CactusStateBehaviour();

    private CactusStateBehaviour() {
    }

    public static CactusStateBehaviour getInstance() {
        return INSTANCE;
    }

    public AxisAlignedBB resolveCollisionBox(int x, int y, int z) {
        float inset = 0.0625F;
        return AxisAlignedBB.b(
                (double) ((float) x + inset),
                (double) y,
                (double) ((float) z + inset),
                (double) ((float) (x + 1) - inset),
                (double) ((float) (y + 1) - inset),
                (double) ((float) (z + 1) - inset)
        );
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId - 1 : (side == 0 ? textureId + 1 : textureId);
    }

    public boolean canRemainPlaced(
            boolean westBuildable,
            boolean eastBuildable,
            boolean northBuildable,
            boolean southBuildable,
            int belowTypeId,
            int cactusBlockId,
            int sandBlockId
    ) {
        if (westBuildable || eastBuildable || northBuildable || southBuildable) {
            return false;
        }
        return belowTypeId == cactusBlockId || belowTypeId == sandBlockId;
    }

    public boolean canRemainPlaced(SupportQuery supportQuery, int x, int y, int z, int cactusBlockId, int sandBlockId) {
        return canRemainPlaced(
                supportQuery.isBuildableMaterial(x - 1, y, z),
                supportQuery.isBuildableMaterial(x + 1, y, z),
                supportQuery.isBuildableMaterial(x, y, z - 1),
                supportQuery.isBuildableMaterial(x, y, z + 1),
                supportQuery.getTypeId(x, y - 1, z),
                cactusBlockId,
                sandBlockId
        );
    }

    public boolean canPlace(boolean superCanPlace, boolean canRemainPlaced) {
        return superCanPlace && canRemainPlaced;
    }

    public int contactDamage() {
        return 1;
    }

    public interface SupportQuery {
        boolean isBuildableMaterial(int x, int y, int z);

        int getTypeId(int x, int y, int z);
    }
}
