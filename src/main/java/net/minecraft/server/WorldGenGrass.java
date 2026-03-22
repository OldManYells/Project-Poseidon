package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.GrassPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenGrass extends WorldGenerator {
    private static final GrassPatchGenerationBehaviour GRASS_PATCH_GENERATION_BEHAVIOUR = GrassPatchGenerationBehaviour.getInstance();

    private int a;
    private int b;

    public WorldGenGrass(int i, int j) {
        this.a = i;
        this.b = j;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return GRASS_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a, this.b);
    }
}
