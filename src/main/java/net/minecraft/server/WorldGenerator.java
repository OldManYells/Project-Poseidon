package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.WorldGeneratorBaseBehaviour;

import java.util.Random;

public abstract class WorldGenerator {
    private static final WorldGeneratorBaseBehaviour WORLD_GENERATOR_BASE_BEHAVIOUR = WorldGeneratorBaseBehaviour.getInstance();

    public WorldGenerator() {
        WORLD_GENERATOR_BASE_BEHAVIOUR.initialize(this);
    }

    public abstract boolean a(World world, Random random, int i, int j, int k);

    public void a(double d0, double d1, double d2) {
        WORLD_GENERATOR_BASE_BEHAVIOUR.configureCoordinateScale(this, d0, d1, d2);
    }
}
