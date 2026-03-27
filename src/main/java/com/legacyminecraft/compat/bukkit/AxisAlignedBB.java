package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat bounding-box scaffold.
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

    public static AxisAlignedBB b(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        AxisAlignedBB box = new AxisAlignedBB();
        box.setBounds(minX, minY, minZ, maxX, maxY, maxZ);
        return box;
    }

    public AxisAlignedBB b(double x, double y, double z) {
        return b(a - x, b - y, c - z, d + x, e + y, f + z);
    }

    public AxisAlignedBB a(double x, double y, double z) {
        return b(a + x, b + y, c + z, d + x, e + y, f + z);
    }

    public AxisAlignedBB shrink(double x, double y, double z) {
        return b(a + x, b + y, c + z, d - x, e - y, f - z);
    }

    @Override
    public AxisAlignedBB clone() {
        return b(a, b, c, d, e, f);
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
}
