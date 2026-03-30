package org.bukkit.generator;

import org.bukkit.craftbukkit.entity.EntityWolf;

import java.util.Random;

public class BiomeTaiga extends BiomeBase {

    public BiomeTaiga() {
        this.t.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(3) == 0 ? new WorldGenTaiga1() : new WorldGenTaiga2());
    }
}
