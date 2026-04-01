package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.*;

public class BlockLeavesBase extends Block {

    protected boolean b;

    public BlockLeavesBase(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.b = flag;
    }

    public boolean a() {
        return false;
    }
}
