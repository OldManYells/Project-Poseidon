package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

import java.util.Random;

public class BlockObsidian extends BlockStone {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    public BlockObsidian(int i, int j) {
        super(i, j);
    }

    public int a(Random random) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveFixedDropCount(1);
    }

    public int a(int i, Random random) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveFixedDropItemId(Block.OBSIDIAN.id);
    }
}
