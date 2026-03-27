package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal crafting statistic implementation.
 */
public final class CraftingStatistic extends Statistic {
    private final int trackedId;

    public CraftingStatistic(int id, String name, int trackedId) {
        super(id, name);
        this.trackedId = trackedId;
    }

    public int getTrackedId() {
        return trackedId;
    }
}
