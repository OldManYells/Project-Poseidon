package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.item.Item;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockSnowBlock extends Block {

    public BlockSnowBlock(int i, int j) {
        super(i, j, Material.SNOW_BLOCK);
        this.a(true);
    }

    public int a(int i, Random random) {
        return Item.SNOW_BALL.id;
    }

    public int a(Random random) {
        return 4;
    }

    public void a(World world, int i, int j, int k, Random random) {
        if (world.a(EnumSkyBlock.BLOCK, i, j, k) > 11) {
            this.g(world, i, j, k, world.getData(i, j, k));
            world.setTypeId(i, j, k, 0);
        }
    }
}
