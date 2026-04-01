package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.*;

public class BlockBreakable extends Block {

    private boolean a;

    public BlockBreakable(int i, int j, Material material, boolean flag) {
        super(i, j, material);
        this.a = flag;
    }

    public boolean a() {
        return false;
    }
}
