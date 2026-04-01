package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockLightStone extends Block {

    public BlockLightStone(int i, int j, Material material) {
        super(i, j, material);
    }

    public int a(Random random) {
        return 2 + random.nextInt(3);
    }

    public int a(int i, Random random) {
        return Item.GLOWSTONE_DUST.id;
    }
}
