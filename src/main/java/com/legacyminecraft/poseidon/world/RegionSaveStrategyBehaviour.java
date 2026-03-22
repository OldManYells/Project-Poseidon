package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for selecting region chunk save strategy.
 */
public final class RegionSaveStrategyBehaviour {
    private static final RegionSaveStrategyBehaviour INSTANCE = new RegionSaveStrategyBehaviour();

    public enum SaveStrategy {
        REWRITE,
        REUSE,
        GROW
    }

    private RegionSaveStrategyBehaviour() {
    }

    public static RegionSaveStrategyBehaviour getInstance() {
        return INSTANCE;
    }

    public SaveStrategy selectStrategy(boolean canRewriteInPlace, int contiguousFreeSectors, int requiredSectors) {
        if (canRewriteInPlace) {
            return SaveStrategy.REWRITE;
        }

        if (contiguousFreeSectors >= requiredSectors) {
            return SaveStrategy.REUSE;
        }

        return SaveStrategy.GROW;
    }
}
