package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.FlowerPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenFlowers extends WorldGenerator {
    private static final FlowerPatchGenerationBehaviour FLOWER_PATCH_GENERATION_BEHAVIOUR = FlowerPatchGenerationBehaviour.getInstance();

    private int a;

    public WorldGenFlowers(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return FLOWER_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a);
    }
}
