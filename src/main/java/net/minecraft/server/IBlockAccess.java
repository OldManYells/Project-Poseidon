package net.minecraft.server;

import com.legacyminecraft.poseidon.world.BlockAccessContract;

public interface IBlockAccess extends BlockAccessContract {

    int getTypeId(int i, int j, int k);

    TileEntity getTileEntity(int i, int j, int k);

    int getData(int i, int j, int k);

    Material getMaterial(int i, int j, int k);

    boolean e(int i, int j, int k);

    default boolean isOpaqueCube(int x, int y, int z) {
        return this.e(x, y, z);
    }
}
