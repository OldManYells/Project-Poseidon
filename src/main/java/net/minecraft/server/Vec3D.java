package net.minecraft.server;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {
    private static List d = new ArrayList();
    private static int e = 0;
    public double a;
    public double b;
    public double c;

    public static Vec3D a(double d0, double d1, double d2) {
        return new Vec3D(d0, d1, d2);
    }

    public static void a() {
        e = 0;
    }

    public static Vec3D create(double d0, double d1, double d2) {
        if (e >= d.size()) {
            d.add(new Vec3D(d0, d1, d2));
        }
        Vec3D vec3d = (Vec3D) d.get(e++);
        vec3d.e(d0, d1, d2);
        return vec3d;
    }

    private Vec3D(double d0, double d1, double d2) {
        if (d0 == -0.0D) d0 = 0.0D;
        if (d1 == -0.0D) d1 = 0.0D;
        if (d2 == -0.0D) d2 = 0.0D;

        this.a = d0;
        this.b = d1;
        this.c = d2;
    }

    private Vec3D e(double d0, double d1, double d2) {
        this.a = d0;
        this.b = d1;
        this.c = d2;
        return this;
    }

    public final Vec3D poseidonSet(double d0, double d1, double d2) {
        return this.e(d0, d1, d2);
    }

    public Vec3D b() {
        double len = this.c();
        return len < 1.0E-4D ? this : this.e(this.a / len, this.b / len, this.c / len);
    }

    public Vec3D add(double d0, double d1, double d2) {
        return new Vec3D(this.a + d0, this.b + d1, this.c + d2);
    }

    public double a(Vec3D vec3d) {
        double d0 = vec3d.a - this.a;
        double d1 = vec3d.b - this.b;
        double d2 = vec3d.c - this.c;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double b(Vec3D vec3d) {
        double d0 = vec3d.a - this.a;
        double d1 = vec3d.b - this.b;
        double d2 = vec3d.c - this.c;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double d(double d0, double d1, double d2) {
        double d3 = d0 - this.a;
        double d4 = d1 - this.b;
        double d5 = d2 - this.c;
        return d3 * d3 + d4 * d4 + d5 * d5;
    }

    public double c() {
        return Math.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    public Vec3D a(Vec3D vec3d, double d0) {
        return new Vec3D(this.a + (vec3d.a - this.a) * d0, this.b + (vec3d.b - this.b) * d0, this.c + (vec3d.c - this.c) * d0);
    }

    public Vec3D b(Vec3D vec3d, double d0) {
        return new Vec3D(this.a + (vec3d.a - this.a) * d0, this.b + (vec3d.b - this.b) * d0, this.c + (vec3d.c - this.c) * d0);
    }

    public Vec3D c(Vec3D vec3d, double d0) {
        return new Vec3D(this.a + (vec3d.a - this.a) * d0, this.b + (vec3d.b - this.b) * d0, this.c + (vec3d.c - this.c) * d0);
    }

    public String toString() {
        return "Vec3D(" + this.a + ", " + this.b + ", " + this.c + ")";
    }
}
