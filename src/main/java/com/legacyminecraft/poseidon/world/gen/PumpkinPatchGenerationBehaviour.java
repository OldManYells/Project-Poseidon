package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.Block;
import net.minecraft.server.World;

import java.util.Random;

public final class PumpkinPatchGenerationBehaviour {
    private static final PumpkinPatchGenerationBehaviour INSTANCE = new PumpkinPatchGenerationBehaviour();

    private PumpkinPatchGenerationBehaviour() {
    }

    public static PumpkinPatchGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for (int l = 0; l < 64; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j + random.nextInt(4) - random.nextInt(4);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if (canPlacePumpkin(world, i1, j1, k1)) {
                world.setRawTypeIdAndData(i1, j1, k1, Block.PUMPKIN.id, random.nextInt(4));
            }
        }

        return true;
    }

    public boolean canPlacePumpkin(World world, int x, int y, int z) {
        return world.isEmpty(x, y, z)
                && world.getTypeId(x, y - 1, z) == Block.GRASS.id
                && Block.PUMPKIN.canPlace(world, x, y, z);
    }
}
