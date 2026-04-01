package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockStone extends Block {

    public BlockStone(int i, int j) {
        super(i, j, Material.STONE);
    }

    public int a(int i, Random random) {
        return Block.COBBLESTONE.id;
    }
}
