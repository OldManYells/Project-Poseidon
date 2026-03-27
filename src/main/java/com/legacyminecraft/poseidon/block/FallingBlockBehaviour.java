package com.legacyminecraft.poseidon.block;


/**
 * Canonical falling-block motion and support policy for legacy wrappers.
 */
public final class FallingBlockBehaviour {
    private static final FallingBlockBehaviour INSTANCE = new FallingBlockBehaviour();

    private FallingBlockBehaviour() {
    }

    public static FallingBlockBehaviour getInstance() {
        return INSTANCE;
    }

    public int updateDelayTicks() {
        return 3;
    }

    public int chunkCheckRadius() {
        return 32;
    }

    public boolean shouldAttemptFall(boolean canFallThroughBelow, int y) {
        return canFallThroughBelow && y >= 0;
    }

    public boolean shouldSpawnFallingEntity(boolean instaFall, boolean surroundingAreaLoaded) {
        return !instaFall && surroundingAreaLoaded;
    }

    public boolean shouldApplyDupingFix(boolean dupingFixEnabled) {
        return dupingFixEnabled;
    }

    public int resolveSettledY(FallThroughQuery query, int x, int y, int z) {
        int currentY = y;
        while (query.canFallThrough(x, currentY - 1, z) && currentY > 0) {
            --currentY;
        }
        return currentY;
    }

    public boolean canSettleAt(int y) {
        return y > 0;
    }

    public boolean canFallThrough(int typeId, int fireBlockId, Material material) {
        return typeId == 0
                || typeId == fireBlockId
                || material == Material.WATER
                || material == Material.LAVA;
    }

    public interface FallThroughQuery {
        boolean canFallThrough(int x, int y, int z);
    }
}
