package net.minecraft.server;

import org.bukkit.craftbukkit.entity.EntityWolf;

import java.util.Random;

public class BiomeForest extends BiomeBase {

    public BiomeForest() {
        this.t.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(5) == 0 ? new WorldGenForest() : (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees()));
    }
}
