package com.legacyminecraft.poseidon.world;


import java.util.List;

public final class AxisAlignedBoundingBoxBehaviour {
    private static final AxisAlignedBoundingBoxBehaviour INSTANCE = new AxisAlignedBoundingBoxBehaviour();

    private AxisAlignedBoundingBoxBehaviour() {
    }

    public static AxisAlignedBoundingBoxBehaviour getInstance() {
        return INSTANCE;
    }

    public static final class PoolState {
        public final AxisAlignedBB value;
        public final int nextIndex;

        public PoolState(AxisAlignedBB value, int nextIndex) {
            this.value = value;
            this.nextIndex = nextIndex;
        }
    }

    public int resetPoolIndex() {
        return 0;
    }

    public PoolState createPooled(List pool, int index, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        if (index >= pool.size()) {
            pool.add(AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
        }

        AxisAlignedBB value = ((AxisAlignedBB) pool.get(index)).c(minX, minY, minZ, maxX, maxY, maxZ);
        return new PoolState(value, index + 1);
    }

    public AxisAlignedBB directionalExpand(AxisAlignedBB box, double x, double y, double z) {
        double minX = box.a;
        double minY = box.b;
        double minZ = box.c;
        double maxX = box.d;
        double maxY = box.e;
        double maxZ = box.f;

        if (x < 0.0D) {
            minX += x;
        }

        if (x > 0.0D) {
            maxX += x;
        }

        if (y < 0.0D) {
            minY += y;
        }

        if (y > 0.0D) {
            maxY += y;
        }

        if (z < 0.0D) {
            minZ += z;
        }

        if (z > 0.0D) {
            maxZ += z;
        }

        return AxisAlignedBB.b(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB expand(AxisAlignedBB box, double x, double y, double z) {
        double minX = box.a - x;
        double minY = box.b - y;
        double minZ = box.c - z;
        double maxX = box.d + x;
        double maxY = box.e + y;
        double maxZ = box.f + z;

        return AxisAlignedBB.b(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB offsetCopy(AxisAlignedBB box, double x, double y, double z) {
        return AxisAlignedBB.b(box.a + x, box.b + y, box.c + z, box.d + x, box.e + y, box.f + z);
    }

    public double clipXCollide(AxisAlignedBB box, AxisAlignedBB other, double offsetX) {
        if (other.e > box.b && other.b < box.e) {
            if (other.f > box.c && other.c < box.f) {
                double maxOffset;

                if (offsetX > 0.0D && other.d <= box.a) {
                    maxOffset = box.a - other.d;
                    if (maxOffset < offsetX) {
                        offsetX = maxOffset;
                    }
                }

                if (offsetX < 0.0D && other.a >= box.d) {
                    maxOffset = box.d - other.a;
                    if (maxOffset > offsetX) {
                        offsetX = maxOffset;
                    }
                }
            }
        }

        return offsetX;
    }

    public double clipYCollide(AxisAlignedBB box, AxisAlignedBB other, double offsetY) {
        if (other.d > box.a && other.a < box.d) {
            if (other.f > box.c && other.c < box.f) {
                double maxOffset;

                if (offsetY > 0.0D && other.e <= box.b) {
                    maxOffset = box.b - other.e;
                    if (maxOffset < offsetY) {
                        offsetY = maxOffset;
                    }
                }

                if (offsetY < 0.0D && other.b >= box.e) {
                    maxOffset = box.e - other.b;
                    if (maxOffset > offsetY) {
                        offsetY = maxOffset;
                    }
                }
            }
        }

        return offsetY;
    }

    public double clipZCollide(AxisAlignedBB box, AxisAlignedBB other, double offsetZ) {
        if (other.d > box.a && other.a < box.d) {
            if (other.e > box.b && other.b < box.e) {
                double maxOffset;

                if (offsetZ > 0.0D && other.f <= box.c) {
                    maxOffset = box.c - other.f;
                    if (maxOffset < offsetZ) {
                        offsetZ = maxOffset;
                    }
                }

                if (offsetZ < 0.0D && other.c >= box.f) {
                    maxOffset = box.f - other.c;
                    if (maxOffset > offsetZ) {
                        offsetZ = maxOffset;
                    }
                }
            }
        }

        return offsetZ;
    }

    public boolean intersects(AxisAlignedBB box, AxisAlignedBB other) {
        return other.d > box.a && other.a < box.d ? (other.e > box.b && other.b < box.e ? other.f > box.c && other.c < box.f : false) : false;
    }

    public AxisAlignedBB move(AxisAlignedBB box, double x, double y, double z) {
        box.a += x;
        box.b += y;
        box.c += z;
        box.d += x;
        box.e += y;
        box.f += z;
        return box;
    }

    public boolean contains(AxisAlignedBB box, Vec3D vector) {
        return vector.a > box.a && vector.a < box.d ? (vector.b > box.b && vector.b < box.e ? vector.c > box.c && vector.c < box.f : false) : false;
    }

    public AxisAlignedBB shrink(AxisAlignedBB box, double x, double y, double z) {
        double minX = box.a + x;
        double minY = box.b + y;
        double minZ = box.c + z;
        double maxX = box.d - x;
        double maxY = box.e - y;
        double maxZ = box.f - z;

        return AxisAlignedBB.b(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public AxisAlignedBB clone(AxisAlignedBB box) {
        return AxisAlignedBB.b(box.a, box.b, box.c, box.d, box.e, box.f);
    }

    public MovingObjectPosition calculateIntercept(AxisAlignedBB box, Vec3D start, Vec3D end) {
        Vec3D hitMinX = start.a(end, box.a);
        Vec3D hitMaxX = start.a(end, box.d);
        Vec3D hitMinY = start.b(end, box.b);
        Vec3D hitMaxY = start.b(end, box.e);
        Vec3D hitMinZ = start.c(end, box.c);
        Vec3D hitMaxZ = start.c(end, box.f);

        if (!isWithinYZ(box, hitMinX)) {
            hitMinX = null;
        }

        if (!isWithinYZ(box, hitMaxX)) {
            hitMaxX = null;
        }

        if (!isWithinXZ(box, hitMinY)) {
            hitMinY = null;
        }

        if (!isWithinXZ(box, hitMaxY)) {
            hitMaxY = null;
        }

        if (!isWithinXY(box, hitMinZ)) {
            hitMinZ = null;
        }

        if (!isWithinXY(box, hitMaxZ)) {
            hitMaxZ = null;
        }

        Vec3D closest = null;

        if (hitMinX != null && (closest == null || start.b(hitMinX) < start.b(closest))) {
            closest = hitMinX;
        }

        if (hitMaxX != null && (closest == null || start.b(hitMaxX) < start.b(closest))) {
            closest = hitMaxX;
        }

        if (hitMinY != null && (closest == null || start.b(hitMinY) < start.b(closest))) {
            closest = hitMinY;
        }

        if (hitMaxY != null && (closest == null || start.b(hitMaxY) < start.b(closest))) {
            closest = hitMaxY;
        }

        if (hitMinZ != null && (closest == null || start.b(hitMinZ) < start.b(closest))) {
            closest = hitMinZ;
        }

        if (hitMaxZ != null && (closest == null || start.b(hitMaxZ) < start.b(closest))) {
            closest = hitMaxZ;
        }

        if (closest == null) {
            return null;
        }

        byte face = -1;

        if (closest == hitMinX) {
            face = 4;
        }

        if (closest == hitMaxX) {
            face = 5;
        }

        if (closest == hitMinY) {
            face = 0;
        }

        if (closest == hitMaxY) {
            face = 1;
        }

        if (closest == hitMinZ) {
            face = 2;
        }

        if (closest == hitMaxZ) {
            face = 3;
        }

        return new MovingObjectPosition(0, 0, 0, face, closest);
    }

    public void copyBounds(AxisAlignedBB target, AxisAlignedBB source) {
        target.a = source.a;
        target.b = source.b;
        target.c = source.c;
        target.d = source.d;
        target.e = source.e;
        target.f = source.f;
    }

    public String stringify(AxisAlignedBB box) {
        return "box[" + box.a + ", " + box.b + ", " + box.c + " -> " + box.d + ", " + box.e + ", " + box.f + "]";
    }

    private static boolean isWithinYZ(AxisAlignedBB box, Vec3D vector) {
        return vector == null ? false : vector.b >= box.b && vector.b <= box.e && vector.c >= box.c && vector.c <= box.f;
    }

    private static boolean isWithinXZ(AxisAlignedBB box, Vec3D vector) {
        return vector == null ? false : vector.a >= box.a && vector.a <= box.d && vector.c >= box.c && vector.c <= box.f;
    }

    private static boolean isWithinXY(AxisAlignedBB box, Vec3D vector) {
        return vector == null ? false : vector.a >= box.a && vector.a <= box.d && vector.b >= box.b && vector.b <= box.e;
    }
}
