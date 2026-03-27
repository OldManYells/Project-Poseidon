package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

public final class ReedPatchGenerationBehaviour {
    private static final ReedPatchGenerationBehaviour INSTANCE = new ReedPatchGenerationBehaviour();

    private ReedPatchGenerationBehaviour() {
    }

    public static ReedPatchGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        for (int l = 0; l < 20; ++l) {
            int i1 = i + random.nextInt(4) - random.nextInt(4);
            int j1 = j;
            int k1 = k + random.nextInt(4) - random.nextInt(4);

            if (world.isEmpty(i1, j, k1) && hasAdjacentWaterAtSoilLevel(world, i1, j, k1)) {
                int l1 = 2 + random.nextInt(random.nextInt(3) + 1);

                for (int i2 = 0; i2 < l1; ++i2) {
                    if (Block.SUGAR_CANE_BLOCK.f(world, i1, j1 + i2, k1)) {
                        world.setRawTypeId(i1, j1 + i2, k1, Block.SUGAR_CANE_BLOCK.id);
                    }
                }
            }
        }

        return true;
    }

    public boolean hasAdjacentWaterAtSoilLevel(World world, int x, int y, int z) {
        return world.getMaterial(x - 1, y - 1, z) == Material.WATER
                || world.getMaterial(x + 1, y - 1, z) == Material.WATER
                || world.getMaterial(x, y - 1, z - 1) == Material.WATER
                || world.getMaterial(x, y - 1, z + 1) == Material.WATER;
    }
}
