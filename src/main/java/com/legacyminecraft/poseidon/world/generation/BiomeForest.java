package com.legacyminecraft.poseidon.world.generation;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.generation.feature.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BiomeForest extends BiomeBase {

    public BiomeForest() {
        this.t.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public WorldGenerator a(Random random) {
        return (WorldGenerator) (random.nextInt(5) == 0 ? new WorldGenForest() : (random.nextInt(3) == 0 ? new WorldGenBigTree() : new WorldGenTrees()));
    }
}
