package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Material;
import net.minecraft.server.TileEntity;

/**
 * Canonical block/world read contract bridged by legacy wrappers.
 */
public interface BlockAccessContract {
    int getTypeId(int x, int y, int z);

    TileEntity getTileEntity(int x, int y, int z);

    int getData(int x, int y, int z);

    Material getMaterial(int x, int y, int z);

    boolean isOpaqueCube(int x, int y, int z);
}
