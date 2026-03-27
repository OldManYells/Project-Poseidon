package com.legacyminecraft.poseidon.item;

/**
 * Item-local vec3 scaffold.
 */
public class Vec3D {
    public double a;
    public double b;
    public double c;

    public Vec3D(double x, double y, double z) {
        this.a = x;
        this.b = y;
        this.c = z;
    }

    public static Vec3D create(double x, double y, double z) {
        return new Vec3D(x, y, z);
    }

    public Vec3D add(double dx, double dy, double dz) {
        return new Vec3D(this.a + dx, this.b + dy, this.c + dz);
    }
}
