package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.block.material.*;
import com.legacyminecraft.poseidon.world.block.entity.*;

public interface IBlockAccess {

    int getTypeId(int i, int j, int k);

    TileEntity getTileEntity(int i, int j, int k);

    int getData(int i, int j, int k);

    Material getMaterial(int i, int j, int k);

    boolean e(int i, int j, int k);
}
