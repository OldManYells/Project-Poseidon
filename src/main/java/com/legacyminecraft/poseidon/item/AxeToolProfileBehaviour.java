package com.legacyminecraft.poseidon.item;

import net.minecraft.server.Block;

public final class AxeToolProfileBehaviour {
    private static final AxeToolProfileBehaviour INSTANCE = new AxeToolProfileBehaviour();
    private static final Block[] EFFECTIVE_BLOCKS = new Block[] { Block.WOOD, Block.BOOKSHELF, Block.LOG, Block.CHEST };

    private AxeToolProfileBehaviour() {
    }

    public static AxeToolProfileBehaviour getInstance() {
        return INSTANCE;
    }

    public Block[] effectiveBlocks() {
        return EFFECTIVE_BLOCKS;
    }

    public int baseAttackOffset() {
        return 3;
    }
}
