package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.CaveLiquidPocketGenerationBehaviour;

import java.util.Random;

public class WorldGenLiquids extends WorldGenerator {
    private static final CaveLiquidPocketGenerationBehaviour CAVE_LIQUID_POCKET_GENERATION_BEHAVIOUR = CaveLiquidPocketGenerationBehaviour.getInstance();

    private int a;

    public WorldGenLiquids(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return CAVE_LIQUID_POCKET_GENERATION_BEHAVIOUR.generateInStone(world, random, i, j, k, this.a);
    }
}
