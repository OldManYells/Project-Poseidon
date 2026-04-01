package com.legacyminecraft.poseidon.world.generation.feature;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public abstract class WorldGenerator {

    public WorldGenerator() {}

    public abstract boolean a(World world, Random random, int i, int j, int k);

    public void a(double d0, double d1, double d2) {}
}
