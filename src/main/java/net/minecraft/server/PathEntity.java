package net.minecraft.server;

import org.bukkit.craftbukkit.entity.Entity;
import org.bukkit.util.Vec3D;

public class PathEntity {

    private final PathPoint[] points;
    public final int a;
    private int pathIndex;

    public PathEntity(PathPoint[] points) {
        this.points = points;
        this.a = points.length;
    }

    public int getCurrentPathLength() {
        return this.a;
    }

    public void incrementPathIndex() {
        ++this.pathIndex;
    }

    public boolean isFinished() {
        return this.pathIndex >= this.points.length;
    }

    public PathPoint getFinalPathPoint() {
        return this.a > 0 ? this.points[this.a - 1] : null;
    }

    public Vec3D getPosition(Entity entity) {
        double x = (double) this.points[this.pathIndex].a + (double) ((int) (entity.length + 1.0F)) * 0.5D;
        double y = (double) this.points[this.pathIndex].b;
        double z = (double) this.points[this.pathIndex].c + (double) ((int) (entity.length + 1.0F)) * 0.5D;
        return Vec3D.create(x, y, z);
    }

    @Deprecated
    public void a() {
        this.incrementPathIndex();
    }

    @Deprecated
    public boolean b() {
        return this.isFinished();
    }

    @Deprecated
    public PathPoint c() {
        return this.getFinalPathPoint();
    }

    @Deprecated
    public Vec3D a(Entity entity) {
        return this.getPosition(entity);
    }
}
