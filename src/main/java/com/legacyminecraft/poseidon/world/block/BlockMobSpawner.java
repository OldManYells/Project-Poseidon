package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.block.entity.*;
import com.legacyminecraft.poseidon.*;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer {

    public BlockMobSpawner(int i, int j) {
        super(i, j, Material.STONE);
    }

    protected TileEntity a_() {
        return new TileEntityMobSpawner();
    }

    public int a(int i, Random random) {
        return 0;
    }

    public int a(Random random) {
        return 0;
    }

    public boolean a() {
        return false;
    }
}
