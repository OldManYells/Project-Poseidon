package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;

public final class SpadeHarvestBehaviour {
    private static final SpadeHarvestBehaviour INSTANCE = new SpadeHarvestBehaviour();

    private SpadeHarvestBehaviour() {
    }

    public static SpadeHarvestBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canHarvest(Block block) {
        return block == Block.SNOW || block == Block.SNOW_BLOCK;
    }
}
