package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockObsidian extends BlockStone {

    public BlockObsidian(int i, int j) {
        super(i, j);
    }

    public int a(Random random) {
        return 1;
    }

    public int a(int i, Random random) {
        return Block.OBSIDIAN.id;
    }
}
