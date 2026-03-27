package com.legacyminecraft.poseidon.world.gen;

/**
 * World-generation local world scaffold.
 */
public class World implements BlockChangeDelegate {
    public final java.util.Random random = new java.util.Random();
    public boolean isStatic;

    @Override
    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    @Override
    public Material getMaterial(int x, int y, int z) {
        return Material.SOLID;
    }

    @Override
    public boolean isEmpty(int x, int y, int z) {
        return true;
    }

    @Override
    public void setRawTypeId(int x, int y, int z, int typeId) {
    }

    @Override
    public void setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
    }

    public void setTypeId(int x, int y, int z, int typeId) {
    }

    public void setTypeIdAndData(int x, int y, int z, int typeId, int data) {
    }

    public Object getTileEntity(int x, int y, int z) {
        return null;
    }

    public boolean a(int x1, int y1, int z1, int x2, int y2, int z2) {
        return true;
    }
}
