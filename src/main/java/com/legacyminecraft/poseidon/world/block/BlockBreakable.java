package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.world.block.material.Material;

public class BlockBreakable extends Block {

    private boolean a;

    public BlockBreakable(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.a = flag;
    }

    public boolean a() {
        return false;
    }
}
