package net.minecraft.server;

public class PathPoint {

    public final int a;
    public final int b;
    public final int c;
    private final int hash;
    int d = -1;
    float e;
    float f;
    float g;
    PathPoint h;
    public boolean i = false;

    public PathPoint(int x, int y, int z) {
        this.a = x;
        this.b = y;
        this.c = z;
        this.hash = makeHash(x, y, z);
    }

    public int getX() {
        return this.a;
    }

    public int getY() {
        return this.b;
    }

    public int getZ() {
        return this.c;
    }

    public static int makeHash(int x, int y, int z) {
        return y & 255 | (x & 32767) << 8 | (z & 32767) << 24 | (x < 0 ? Integer.MIN_VALUE : 0) | (z < 0 ? '\u8000' : 0);
    }

    public float distanceTo(PathPoint other) {
        float deltaX = (float) (other.a - this.a);
        float deltaY = (float) (other.b - this.b);
        float deltaZ = (float) (other.c - this.c);
        return MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public boolean isAssigned() {
        return this.d >= 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof PathPoint)) {
            return false;
        }

        PathPoint other = (PathPoint) object;
        return this.hash == other.hash && this.a == other.a && this.b == other.b && this.c == other.c;
    }

    public int hashCode() {
        return this.hash;
    }

    public String toString() {
        return this.a + ", " + this.b + ", " + this.c;
    }

    @Deprecated
    public static int a(int x, int y, int z) {
        return makeHash(x, y, z);
    }

    @Deprecated
    public float a(PathPoint other) {
        return this.distanceTo(other);
    }

    @Deprecated
    public boolean a() {
        return this.isAssigned();
    }
}
