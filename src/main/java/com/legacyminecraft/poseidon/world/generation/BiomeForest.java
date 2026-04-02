package com.legacyminecraft.poseidon.world.generation;

import com.legacyminecraft.poseidon.world.entity.EntityWolf;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenBigTree;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenForest;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenTrees;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenerator;

import java.util.Random;

public class BiomeForest extends BiomeBase {

    public BiomeForest() {
        this.t.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(5) == 0 ? new WorldGenForest() : (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees()));
    }
}
