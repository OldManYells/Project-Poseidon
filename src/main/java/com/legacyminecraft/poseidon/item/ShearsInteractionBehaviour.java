package com.legacyminecraft.poseidon.item;

import com.legacyminecraft.poseidon.block.Block;

public final class ShearsInteractionBehaviour {
    private static final ShearsInteractionBehaviour INSTANCE = new ShearsInteractionBehaviour();

    private ShearsInteractionBehaviour() {
    }

    public static ShearsInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldDamageOnBlockBreak(int blockId) {
        return blockId == Block.LEAVES.id || blockId == Block.WEB.id;
    }

    public boolean canHarvest(Block block) {
        return block.id == Block.WEB.id;
    }

    public float breakSpeedMultiplier(Block block, float fallbackSpeed) {
        if (block.id == Block.WEB.id || block.id == Block.LEAVES.id) {
            return 15.0F;
        }
        if (block.id == Block.WOOL.id) {
            return 5.0F;
        }
        return fallbackSpeed;
    }
}
