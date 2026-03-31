package org.bukkit.util;

import net.minecraft.server.MathHelper;

import java.util.ArrayList;
import java.util.List;

public class Vec3D {

    private static final List<Vec3D> VECTOR_POOL = new ArrayList<Vec3D>();
    private static int nextPoolIndex = 0;

    /**
     * Legacy obfuscated public fields.
     * Use getX(), getY(), getZ() in new code.
     */
    public double a; // x
    public double b; // y
    public double c; // z

    public static Vec3D of(double x, double y, double z) {
        return new Vec3D(x, y, z);
    }

    /**
     * @deprecated Use {@link #of(double, double, double)}.
     */
    @Deprecated
    public static Vec3D a(double x, double y, double z) {
        return of(x, y, z);
    }

    public static void resetPool() {
        nextPoolIndex = 0;
    }

    /**
     * @deprecated Use {@link #resetPool()}.
     */
    @Deprecated
    public static void a() {
        resetPool();
    }

    public static Vec3D fromPool(double x, double y, double z) {
        if (nextPoolIndex >= VECTOR_POOL.size()) {
            VECTOR_POOL.add(of(0.0D, 0.0D, 0.0D));
        }

        return VECTOR_POOL.get(nextPoolIndex++).setComponents(x, y, z);
    }

    /**
     * Kept because this name is already readable and may already be used externally.
     * You can switch callers to {@link #fromPool(double, double, double)} over time.
     */
    public static Vec3D create(double x, double y, double z) {
        return fromPool(x, y, z);
    }

    private Vec3D(double x, double y, double z) {
        if (x == -0.0D) {
            x = 0.0D;
        }

        if (y == -0.0D) {
            y = 0.0D;
        }

        if (z == -0.0D) {
            z = 0.0D;
        }

        this.a = x;
        this.b = y;
        this.c = z;
    }

    private Vec3D setComponents(double x, double y, double z) {
        this.a = x;
        this.b = y;
        this.c = z;
        return this;
    }

    /**
     * @deprecated Use {@link #setComponents(double, double, double)}.
     */
    @Deprecated
    private Vec3D e(double x, double y, double z) {
        return setComponents(x, y, z);
    }

    public double getX() {
        return this.a;
    }

    public double getY() {
        return this.b;
    }

    public double getZ() {
        return this.c;
    }

    public Vec3D normalize() {
        double length = (double) MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c);

        return length < 1.0E-4D
                ? fromPool(0.0D, 0.0D, 0.0D)
                : fromPool(this.a / length, this.b / length, this.c / length);
    }

    /**
     * @deprecated Use {@link #normalize()}.
     */
    @Deprecated
    public Vec3D b() {
        return normalize();
    }

    public Vec3D add(double x, double y, double z) {
        return fromPool(this.a + x, this.b + y, this.c + z);
    }

    public double distanceTo(Vec3D other) {
        double deltaX = other.a - this.a;
        double deltaY = other.b - this.b;
        double deltaZ = other.c - this.c;

        return (double) MathHelper.a(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    /**
     * @deprecated Use {@link #distanceTo(Vec3D)}.
     */
    @Deprecated
    public double a(Vec3D other) {
        return distanceTo(other);
    }

    public double distanceSquaredTo(Vec3D other) {
        double deltaX = other.a - this.a;
        double deltaY = other.b - this.b;
        double deltaZ = other.c - this.c;

        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    /**
     * @deprecated Use {@link #distanceSquaredTo(Vec3D)}.
     */
    @Deprecated
    public double b(Vec3D other) {
        return distanceSquaredTo(other);
    }

    public double distanceSquaredTo(double x, double y, double z) {
        double deltaX = x - this.a;
        double deltaY = y - this.b;
        double deltaZ = z - this.c;

        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    /**
     * @deprecated Use {@link #distanceSquaredTo(double, double, double)}.
     */
    @Deprecated
    public double d(double x, double y, double z) {
        return distanceSquaredTo(x, y, z);
    }

    public double length() {
        return (double) MathHelper.a(this.a * this.a + this.b * this.b + this.c * this.c);
    }

    /**
     * @deprecated Use {@link #length()}.
     */
    @Deprecated
    public double c() {
        return length();
    }

    public Vec3D getIntermediateWithXValue(Vec3D other, double x) {
        double deltaX = other.a - this.a;
        double deltaY = other.b - this.b;
        double deltaZ = other.c - this.c;

        if (deltaX * deltaX < 1.0000000116860974E-7D) {
            return null;
        } else {
            double factor = (x - this.a) / deltaX;
            return factor >= 0.0D && factor <= 1.0D
                    ? fromPool(this.a + deltaX * factor, this.b + deltaY * factor, this.c + deltaZ * factor)
                    : null;
        }
    }

    /**
     * @deprecated Use {@link #getIntermediateWithXValue(Vec3D, double)}.
     */
    @Deprecated
    public Vec3D a(Vec3D other, double x) {
        return getIntermediateWithXValue(other, x);
    }

    public Vec3D getIntermediateWithYValue(Vec3D other, double y) {
        double deltaX = other.a - this.a;
        double deltaY = other.b - this.b;
        double deltaZ = other.c - this.c;

        if (deltaY * deltaY < 1.0000000116860974E-7D) {
            return null;
        } else {
            double factor = (y - this.b) / deltaY;
            return factor >= 0.0D && factor <= 1.0D
                    ? fromPool(this.a + deltaX * factor, this.b + deltaY * factor, this.c + deltaZ * factor)
                    : null;
        }
    }

    /**
     * @deprecated Use {@link #getIntermediateWithYValue(Vec3D, double)}.
     */
    @Deprecated
    public Vec3D b(Vec3D other, double y) {
        return getIntermediateWithYValue(other, y);
    }

    public Vec3D getIntermediateWithZValue(Vec3D other, double z) {
        double deltaX = other.a - this.a;
        double deltaY = other.b - this.b;
        double deltaZ = other.c - this.c;

        if (deltaZ * deltaZ < 1.0000000116860974E-7D) {
            return null;
        } else {
            double factor = (z - this.c) / deltaZ;
            return factor >= 0.0D && factor <= 1.0D
                    ? fromPool(this.a + deltaX * factor, this.b + deltaY * factor, this.c + deltaZ * factor)
                    : null;
        }
    }

    /**
     * @deprecated Use {@link #getIntermediateWithZValue(Vec3D, double)}.
     */
    @Deprecated
    public Vec3D c(Vec3D other, double z) {
        return getIntermediateWithZValue(other, z);
    }

    @Override
    public String toString() {
        return "(" + this.a + ", " + this.b + ", " + this.c + ")";
    }
}