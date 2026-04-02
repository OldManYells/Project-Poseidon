package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.world.block.material.Material;

import java.util.Random;

public class BlockBookshelf extends Block {

    public BlockBookshelf(int i, int j) {
        super(i, j, Material.WOOD);
    }

    public int a(int i) {
        return i <= 1 ? 4 : this.textureId;
    }

    public int a(Random random) {
        return 0;
    }
}
