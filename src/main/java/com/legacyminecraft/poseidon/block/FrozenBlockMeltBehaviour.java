package com.legacyminecraft.poseidon.block;

/**
 * Canonical melt and thaw policy shared by legacy frozen-block wrappers.
 */
public final class FrozenBlockMeltBehaviour {
    private static final FrozenBlockMeltBehaviour INSTANCE = new FrozenBlockMeltBehaviour();

    private FrozenBlockMeltBehaviour() {
    }

    public static FrozenBlockMeltBehaviour getInstance() {
        return INSTANCE;
    }

    public int snowMeltThreshold() {
        return 11;
    }

    public int iceMeltThreshold(int lightOpacity) {
        return 11 - lightOpacity;
    }

    public boolean shouldMelt(int blockLight, int threshold) {
        return blockLight > threshold;
    }

    public int resolveSnowBlockDropCount() {
        return 4;
    }

    public boolean shouldConvertHarvestedIceToWater(boolean belowSolid, boolean belowLiquid) {
        return belowSolid || belowLiquid;
    }
}
