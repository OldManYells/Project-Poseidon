package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.ReedPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenReed extends WorldGenerator {
    private static final ReedPatchGenerationBehaviour REED_PATCH_GENERATION_BEHAVIOUR = ReedPatchGenerationBehaviour.getInstance();

    public WorldGenReed() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return REED_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
