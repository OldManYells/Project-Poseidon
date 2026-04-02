package com.legacyminecraft.poseidon.world.block;

import com.legacyminecraft.poseidon.world.item.Item;

import java.util.Random;

public class BlockGravel extends BlockSand {

    public BlockGravel(int i, int j) {
        super(i, j);
    }

    public int a(int i, Random random) {
        return random.nextInt(10) == 0 ? Item.FLINT.id : this.id;
    }
}
