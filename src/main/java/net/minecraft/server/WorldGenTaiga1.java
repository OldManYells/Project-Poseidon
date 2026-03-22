package net.minecraft.server;

import com.legacyminecraft.poseidon.world.gen.TaigaTreeGenerationBehaviour;
import org.bukkit.BlockChangeDelegate;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenerator {
    private static final TaigaTreeGenerationBehaviour TAIGA_TREE_GENERATION_BEHAVIOUR = TaigaTreeGenerationBehaviour.getInstance();

    public WorldGenTaiga1() {
    }

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
        return TAIGA_TREE_GENERATION_BEHAVIOUR.generateTaiga1(world, random, i, j, k);
    }
}
