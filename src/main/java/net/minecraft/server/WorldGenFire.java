package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.FirePatchGenerationBehaviour;

import java.util.Random;

public class WorldGenFire extends WorldGenerator {
    private static final FirePatchGenerationBehaviour FIRE_PATCH_GENERATION_BEHAVIOUR = FirePatchGenerationBehaviour.getInstance();

    public WorldGenFire() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return FIRE_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
