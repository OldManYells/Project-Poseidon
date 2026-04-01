package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockClay extends Block {

    public BlockClay(int i, int j) {
        super(i, j, Material.CLAY);
    }

    public int a(int i, Random random) {
        return Item.CLAY_BALL.id;
    }

    public int a(Random random) {
        return 4;
    }
}
