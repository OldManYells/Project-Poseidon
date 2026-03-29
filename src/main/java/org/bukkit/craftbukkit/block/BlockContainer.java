package org.bukkit.craftbukkit.block;

import net.minecraft.server.CraftBlock;
import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;
import net.minecraft.server.World;

public abstract class BlockContainer extends CraftBlock {

    protected BlockContainer(int i, Material material) {
        super(i, material);
        isTileEntity[i] = true;
    }

    protected BlockContainer(int i, int j, Material material) {
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
