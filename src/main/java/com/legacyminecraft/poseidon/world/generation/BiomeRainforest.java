package com.legacyminecraft.poseidon.world.generation;

import com.legacyminecraft.poseidon.world.generation.feature.WorldGenBigTree;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenTrees;
import com.legacyminecraft.poseidon.world.generation.feature.WorldGenerator;

import java.util.Random;

public class BiomeRainforest extends BiomeBase {

    public BiomeRainforest() {}

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees());
    }
}
