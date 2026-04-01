package com.legacyminecraft.poseidon.world.generation;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BiomeRainforest extends BiomeBase {

    public BiomeRainforest() {}

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees());
    }
}
