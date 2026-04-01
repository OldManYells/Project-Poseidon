package com.legacyminecraft.poseidon.world.block;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.core.*;
import com.legacyminecraft.poseidon.world.block.entity.*;
import com.legacyminecraft.poseidon.*;

public abstract class BlockContainer extends Block {

    public BlockContainer(int i, Material material) {
        super(i, material);
        isTileEntity[i] = true;
    }

    public BlockContainer(int i, int j, Material material) {
        super(i, j, material);
        isTileEntity[i] = true;
    }

    public void c(World world, int i, int j, int k) {
        super.c(world, i, j, k);
        world.setTileEntity(i, j, k, this.a_());
    }

    public void remove(World world, int i, int j, int k) {
        super.remove(world, i, j, k);
        world.o(i, j, k);
    }

    protected abstract TileEntity a_();
}
