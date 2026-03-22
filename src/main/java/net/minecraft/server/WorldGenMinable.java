package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.OreVeinGenerationBehaviour;

import java.util.Random;

public class WorldGenMinable extends WorldGenerator {
    private static final OreVeinGenerationBehaviour ORE_VEIN_GENERATION_BEHAVIOUR = OreVeinGenerationBehaviour.getInstance();

    private int a;
    private int b;

    public WorldGenMinable(int i, int j) {
        this.a = i;
        this.b = j;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return ORE_VEIN_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a, this.b);
    }
}
