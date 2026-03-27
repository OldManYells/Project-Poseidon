package com.legacyminecraft.poseidon.world;


import java.util.List;

public final class Vec3DBehaviour {
    private static final Vec3DBehaviour INSTANCE = new Vec3DBehaviour();

    private Vec3DBehaviour() {
    }

    public static Vec3DBehaviour getInstance() {
        return INSTANCE;
    }

    public double sanitizeNegativeZero(double value) {
        return value == -0.0D ? 0.0D : value;
    }

    public int resetPoolIndex() {
        return 0;
    }

    public PoolState createPooled(List pool, int index, double x, double y, double z) {
        if (index >= pool.size()) {
            pool.add(Vec3D.a(0.0D, 0.0D, 0.0D));
        }

        Vec3D vec = ((Vec3D) pool.get(index++)).poseidonSet(x, y, z);
        return new PoolState(index, vec);
    }

    public Vec3D normalize(Vec3D vec) {
        double magnitude = (double) MathHelper.a(vec.a * vec.a + vec.b * vec.b + vec.c * vec.c);
        return magnitude < 1.0E-4D ? Vec3D.create(0.0D, 0.0D, 0.0D) : Vec3D.create(vec.a / magnitude, vec.b / magnitude, vec.c / magnitude);
    }

    public Vec3D add(Vec3D vec, double x, double y, double z) {
        return Vec3D.create(vec.a + x, vec.b + y, vec.c + z);
    }

    public double distance(Vec3D from, Vec3D to) {
        double deltaX = to.a - from.a;
        double deltaY = to.b - from.b;
        double deltaZ = to.c - from.c;

        return (double) MathHelper.a(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public double distanceSquared(Vec3D from, Vec3D to) {
        double deltaX = to.a - from.a;
        double deltaY = to.b - from.b;
        double deltaZ = to.c - from.c;

        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public double distanceSquared(Vec3D from, double x, double y, double z) {
        double deltaX = x - from.a;
        double deltaY = y - from.b;
        double deltaZ = z - from.c;

        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public double length(Vec3D vec) {
        return (double) MathHelper.a(vec.a * vec.a + vec.b * vec.b + vec.c * vec.c);
    }

    public Vec3D interpolateX(Vec3D from, Vec3D to, double x) {
        double deltaX = to.a - from.a;
        double deltaY = to.b - from.b;
        double deltaZ = to.c - from.c;

        if (deltaX * deltaX < 1.0000000116860974E-7D) {
            return null;
        }

        double scale = (x - from.a) / deltaX;
        return scale >= 0.0D && scale <= 1.0D ? Vec3D.create(from.a + deltaX * scale, from.b + deltaY * scale, from.c + deltaZ * scale) : null;
    }

    public Vec3D interpolateY(Vec3D from, Vec3D to, double y) {
        double deltaX = to.a - from.a;
        double deltaY = to.b - from.b;
        double deltaZ = to.c - from.c;

        if (deltaY * deltaY < 1.0000000116860974E-7D) {
            return null;
        }

        double scale = (y - from.b) / deltaY;
        return scale >= 0.0D && scale <= 1.0D ? Vec3D.create(from.a + deltaX * scale, from.b + deltaY * scale, from.c + deltaZ * scale) : null;
    }

    public Vec3D interpolateZ(Vec3D from, Vec3D to, double z) {
        double deltaX = to.a - from.a;
        double deltaY = to.b - from.b;
        double deltaZ = to.c - from.c;

        if (deltaZ * deltaZ < 1.0000000116860974E-7D) {
            return null;
        }

        double scale = (z - from.c) / deltaZ;
        return scale >= 0.0D && scale <= 1.0D ? Vec3D.create(from.a + deltaX * scale, from.b + deltaY * scale, from.c + deltaZ * scale) : null;
    }

    public String stringify(Vec3D vec) {
        return "(" + vec.a + ", " + vec.b + ", " + vec.c + ")";
    }

    public static final class PoolState {
        public final int nextIndex;
        public final Vec3D value;

        PoolState(int nextIndex, Vec3D value) {
            this.nextIndex = nextIndex;
            this.value = value;
        }
    }
}
