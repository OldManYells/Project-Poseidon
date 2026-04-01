package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockGlass extends BlockBreakable {

    public BlockGlass(int i, int j, Material material, boolean flag) {
        super(i, j, material, flag);
    }

    public int a(Random random) {
        return 0;
    }
}
