package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local bounding box scaffold.
 */
public class AxisAlignedBB implements Cloneable {
    public double a;
    public double b;
    public double c;
    public double d;
    public double e;
    public double f;

    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    public AxisAlignedBB() {
    }

    public AxisAlignedBB(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        setBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public static AxisAlignedBB b(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB b(double x, double y, double z) {
        return new AxisAlignedBB(minX - x, minY - y, minZ - z, maxX + x, maxY + y, maxZ + z);
    }

    public AxisAlignedBB a(double x, double y, double z) {
        return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
    }

    public AxisAlignedBB c(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        setBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return this;
    }

    public AxisAlignedBB c(double x, double y, double z) {
        return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX + x, maxY + y, maxZ + z);
    }

    public AxisAlignedBB shrink(double x, double y, double z) {
        return new AxisAlignedBB(minX + x, minY + y, minZ + z, maxX - x, maxY - y, maxZ - z);
    }

    private void setBounds(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.a = minX;
        this.b = minY;
        this.c = minZ;
        this.d = maxX;
        this.e = maxY;
        this.f = maxZ;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    @Override
    public AxisAlignedBB clone() {
        return new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
