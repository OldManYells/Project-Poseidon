package net.minecraft.server;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;

public class BlockBreakable extends Block {

    private boolean a;
    private static final SimpleBlockStateBehaviour SIMPLE_BLOCK_STATE_SERVICE = SimpleBlockStateBehaviour.getInstance();

    protected BlockBreakable(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.a = flag;
    }

    public boolean a() {
        return SIMPLE_BLOCK_STATE_SERVICE.isOpaqueCubeFalse();
    }
}
