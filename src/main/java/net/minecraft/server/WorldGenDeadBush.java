package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.DeadBushPatchGenerationBehaviour;

import java.util.Random;

public class WorldGenDeadBush extends WorldGenerator {
    private static final DeadBushPatchGenerationBehaviour DEAD_BUSH_PATCH_GENERATION_BEHAVIOUR = DeadBushPatchGenerationBehaviour.getInstance();

    private int a;

    public WorldGenDeadBush(int i) {
        this.a = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return DEAD_BUSH_PATCH_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a);
    }
}
