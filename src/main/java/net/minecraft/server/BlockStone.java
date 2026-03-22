package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

import java.util.Random;

public class BlockStone extends Block {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    public BlockStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveStoneDropItemId(Block.COBBLESTONE.id);
    }
}
