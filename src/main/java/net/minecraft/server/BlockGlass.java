package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

import java.util.Random;

public class BlockGlass extends BlockBreakable {
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    public BlockGlass(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    public int a(Random random) {
        return SIMPLE_BLOCK_STATE_SERVICE.resolveNoDropCount();
    }
}
