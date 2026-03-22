package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import net.minecraft.server.BlockFlower;
import net.minecraft.server.World;

import java.util.Random;

public final class GrassPatchGenerationBehaviour {
    private static final GrassPatchGenerationBehaviour INSTANCE = new GrassPatchGenerationBehaviour();

    private GrassPatchGenerationBehaviour() {
    }

    public static GrassPatchGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int i, int j, int k, int blockId, int blockData) {
        int l;

        while (((l = world.getTypeId(i, j, k)) == 0 || l == Block.LEAVES.id) && j > 0) {
            --j;
        }

        for (int i1 = 0; i1 < 128; ++i1) {
            int j1 = i + random.nextInt(8) - random.nextInt(8);
            int k1 = j + random.nextInt(4) - random.nextInt(4);
            int l1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.isEmpty(j1, k1, l1) && ((BlockFlower) Block.byId[blockId]).f(world, j1, k1, l1)) {
                world.setRawTypeIdAndData(j1, k1, l1, blockId, blockData);
            }
        }

        return true;
    }
}
