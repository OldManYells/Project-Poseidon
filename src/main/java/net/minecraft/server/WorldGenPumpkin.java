package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.PumpkinPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator {
    private static final PumpkinPatchGenerationBehaviour PUMPKIN_PATCH_GENERATION_BEHAVIOUR = PumpkinPatchGenerationBehaviour.getInstance();

    public WorldGenPumpkin() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return PUMPKIN_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
