package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.ClayVeinGenerationBehaviour;

import java.util.Random;

public class WorldGenClay extends WorldGenerator {
    private static final ClayVeinGenerationBehaviour CLAY_VEIN_GENERATION_BEHAVIOUR = ClayVeinGenerationBehaviour.getInstance();

    private int a;
    private int b;

    public WorldGenClay(int i) {
        this.a = Block.CLAY.id;
        this.b = i;
    }

    public boolean a(World world, Random random, int i, int j, int k) {
        return CLAY_VEIN_GENERATION_BEHAVIOUR.generate(world, random, i, j, k, this.a, this.b);
    }
}
