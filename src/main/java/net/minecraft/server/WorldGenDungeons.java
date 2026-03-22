package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.DungeonGenerationBehaviour;

import java.util.Random;

public class WorldGenDungeons extends WorldGenerator {
    private static final DungeonGenerationBehaviour DUNGEON_GENERATION_BEHAVIOUR = DungeonGenerationBehaviour.getInstance();

    public WorldGenDungeons() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        return DUNGEON_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
