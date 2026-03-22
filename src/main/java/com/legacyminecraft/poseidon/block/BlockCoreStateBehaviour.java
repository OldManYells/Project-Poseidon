package com.legacyminecraft.poseidon.block;

/**
 * Canonical shared math and placement/drop policy for the legacy Block base wrapper.
 */
public final class BlockCoreStateBehaviour {
    private static final BlockCoreStateBehaviour INSTANCE = new BlockCoreStateBehaviour();

    private BlockCoreStateBehaviour() {
    }

    public static BlockCoreStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isNeighborBuildable(boolean buildableMaterial) {
        return buildableMaterial;
    }

    public float resolveDamageProgress(float strength, boolean canHarvest, float harvestSpeed) {
        if (strength < 0.0F) {
            return 0.0F;
        }
        if (!canHarvest) {
            return 1.0F / strength / 100.0F;
        }
        return harvestSpeed / strength / 30.0F;
    }

    public boolean shouldProcessDrops(boolean worldIsStatic) {
        return !worldIsStatic;
    }

    public boolean shouldDropItem(float randomRoll, float dropChance) {
        return randomRoll < dropChance;
    }

    public double resolveDropOffset(float randomRoll, float spread) {
        return (double) (randomRoll * spread) + (double) (1.0F - spread) * 0.5D;
    }

    public int resolvePickupDelay() {
        return 10;
    }

    public boolean canReplace(int existingTypeId, boolean existingIsReplacable) {
        return existingTypeId == 0 || existingIsReplacable;
    }

    public boolean isWithinYZ(double y, double z, double minY, double maxY, double minZ, double maxZ) {
        return y >= minY && y <= maxY && z >= minZ && z <= maxZ;
    }

    public boolean isWithinXZ(double x, double z, double minX, double maxX, double minZ, double maxZ) {
        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    public boolean isWithinXY(double x, double y, double minX, double maxX, double minY, double maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }
}
