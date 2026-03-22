package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.CactusPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenCactus extends WorldGenerator {
    private static final CactusPatchGenerationBehaviour CACTUS_PATCH_GENERATION_BEHAVIOUR = CactusPatchGenerationBehaviour.getInstance();

    public WorldGenCactus() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return CACTUS_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
