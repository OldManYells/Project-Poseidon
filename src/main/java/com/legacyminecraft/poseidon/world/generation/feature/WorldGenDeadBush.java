package com.legacyminecraft.poseidon.world.generation.feature;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.Block;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.block.*;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator {

    private int a;

    public WorldGenDeadBush(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        int l;

        for (boolean flag = false; ((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id) && j > 0; --j) {
            ;
        }

        for (int i1 = 0; i1 < 4; ++i1) {
            int j1 = i + random.nextInt(8) - random.nextInt(8);
            int k1 = j + random.nextInt(4) - random.nextInt(4);
            int l1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.isEmpty(j1, k1, l1) && ((BlockFlower) Block.byId[this.a]).f(world, j1, k1, l1)) {
                world.setRawTypeId(j1, k1, l1, this.a);
            }
        }

        return true;
    }
}
