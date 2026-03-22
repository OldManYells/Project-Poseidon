package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.GlowstoneClusterGenerationBehaviour;

import java.util.Random;

public class WorldGenLightStone2 extends WorldGenerator {
    private static final GlowstoneClusterGenerationBehaviour GLOWSTONE_CLUSTER_GENERATION_BEHAVIOUR = GlowstoneClusterGenerationBehaviour.getInstance();

    public WorldGenLightStone2() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return GLOWSTONE_CLUSTER_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
