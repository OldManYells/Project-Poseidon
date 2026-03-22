package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

public class BlockLeavesBase extends Block {

    protected boolean b;
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    protected BlockLeavesBase(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.b = flag;
    }

    public boolean a() {
        return SIMPLE_BLOCK_STATE_SERVICE.isOpaqueCubeFalse();
    }
}
