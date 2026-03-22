package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.LakeGenerationBehaviour;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator {

    private static final LakeGenerationBehaviour LAKE_GENERATION_BEHAVIOUR = LakeGenerationBehaviour.getInstance();

    private int a;

    public WorldGenLakes(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return LAKE_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a);
    }
}
