package com.legacyminecraft.poseidon.api;

import com.legacyminecraft.poseidon.world.block.entity.TileEntity;
import com.legacyminecraft.poseidon.world.block.material.Material;

public interface IBlockAccess {

    int getTypeId(int i, int j, int k);

    TileEntity getTileEntity(int i, int j, int k);

    int getData(int i, int j, int k);

    Material getMaterial(int i, int j, int k);

    boolean e(int i, int j, int k);
}
