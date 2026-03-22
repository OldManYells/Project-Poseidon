package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.DefaultTreeGenerationBehaviour;
import org.bukkit.BlockChangeDelegate;

import java.util.Random;

public class WorldGenTrees extends WorldGenerator {
    private static final DefaultTreeGenerationBehaviour DEFAULT_TREE_GENERATION_BEHAVIOUR = DefaultTreeGenerationBehaviour.getInstance();

    public WorldGenTrees() {}

    public boolean a(World world, Random random, int i, int j, int k) {
        // CraftBukkit start
        // sk: The idea is to have (our) WorldServer implement
        // BlockChangeDelegate and then we can implicitly cast World to
        // WorldServer (a safe cast, AFAIK) and no code will be broken. This
        // then allows plugins to catch manually-invoked generation events
        return this.generate((BlockChangeDelegate) world, random, i, j, k);
    }

    public boolean generate(BlockChangeDelegate world, Random random, int i, int j, int k) {
        // CraftBukkit end
        return DEFAULT_TREE_GENERATION_BEHAVIOUR.generate(world, random, i, j, k);
    }
}
