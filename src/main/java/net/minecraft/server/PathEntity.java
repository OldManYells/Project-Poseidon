package net.minecraft.server;

public class PathEntity {

    private final PathPoint[] b;
    public final int a;
    private int c;

    public PathEntity(PathPoint[] apathpoint) {
        this.b = apathpoint;
        this.a = apathpoint.length;
    }

    public void a() {
        ++this.c;
    }

    public boolean b() {
        return this.c >= this.b.length;
    }

    public PathPoint c() {
        return this.a > 0 ? this.b[this.a - 1] : null;
    }

    public Vec3D a(Entity entity) {
        double x = (double) this.b[this.c].a + (double) ((int) (entity.length + 1.0F)) * 0.5D;
        double y = (double) this.b[this.c].b;
        double z = (double) this.b[this.c].c + (double) ((int) (entity.length + 1.0F)) * 0.5D;
        return Vec3D.create(x, y, z);
    }
}
