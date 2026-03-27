package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation block mutation/view contract.
 */
public interface BlockChangeDelegate {
    int getTypeId(int x, int y, int z);

    void setRawTypeId(int x, int y, int z, int typeId);

    void setRawTypeIdAndData(int x, int y, int z, int typeId, int data);

    boolean isEmpty(int x, int y, int z);

    Material getMaterial(int x, int y, int z);
}
