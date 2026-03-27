package com.legacyminecraft.poseidon.world.gen;


import java.util.Random;

public final class GlowstoneClusterGenerationBehaviour {
    private static final GlowstoneClusterGenerationBehaviour INSTANCE = new GlowstoneClusterGenerationBehaviour();

    private GlowstoneClusterGenerationBehaviour() {
    }

    public static GlowstoneClusterGenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean generate(World world, Random random, int i, int j, int k) {
        if (!world.isEmpty(i, j, k)) {
            return false;
        }
        if (world.getTypeId(i, j + 1, k) != Block.NETHERRACK.id) {
            return false;
        }

        world.setTypeId(i, j, k, Block.GLOWSTONE.id);

        for (int l = 0; l < 1500; ++l) {
            int i1 = i + random.nextInt(8) - random.nextInt(8);
            int j1 = j - random.nextInt(12);
            int k1 = k + random.nextInt(8) - random.nextInt(8);

            if (world.getTypeId(i1, j1, k1) == 0) {
                int neighbours = countAdjacentGlowstone(world, i1, j1, k1);
                if (neighbours == 1) {
                    world.setTypeId(i1, j1, k1, Block.GLOWSTONE.id);
                }
            }
        }

        return true;
    }

    public int countAdjacentGlowstone(World world, int x, int y, int z) {
        int neighbours = 0;

        if (world.getTypeId(x - 1, y, z) == Block.GLOWSTONE.id) {
            ++neighbours;
        }
        if (world.getTypeId(x + 1, y, z) == Block.GLOWSTONE.id) {
            ++neighbours;
        }
        if (world.getTypeId(x, y - 1, z) == Block.GLOWSTONE.id) {
            ++neighbours;
        }
        if (world.getTypeId(x, y + 1, z) == Block.GLOWSTONE.id) {
            ++neighbours;
        }
        if (world.getTypeId(x, y, z - 1) == Block.GLOWSTONE.id) {
            ++neighbours;
        }
        if (world.getTypeId(x, y, z + 1) == Block.GLOWSTONE.id) {
            ++neighbours;
        }

        return neighbours;
    }
}
