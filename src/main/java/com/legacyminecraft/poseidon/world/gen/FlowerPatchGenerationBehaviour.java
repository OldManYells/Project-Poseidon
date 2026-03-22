package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.World;

import java.util.Random;

public final class FlowerPatchGenerationBehaviour {
    private static final FlowerPatchGenerationBehaviour INSTANCE = new FlowerPatchGenerationBehaviour();

    private FlowerPatchGenerationBehaviour() {
    }

    public static FlowerPatchGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int i, int j, int k, int flowerBlockId) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.isEmpty(i1, j1, k1) && ((BlockFlower) Block.byId[flowerBlockId]).f(world, i1, j1, k1)) {
                world.setRawTypeId(i1, j1, k1, flowerBlockId);
            }
        }

        return true;
    }
}
