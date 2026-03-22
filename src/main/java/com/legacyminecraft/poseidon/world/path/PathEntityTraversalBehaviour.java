package com.legacyminecraft.poseidon.world.path;

import net.minecraft.server.Entity;
import net.minecraft.server.PathPoint;
import net.minecraft.server.Vec3D;

public final class PathEntityTraversalBehaviour {
    private static final PathEntityTraversalBehaviour INSTANCE = new PathEntityTraversalBehaviour();

    private PathEntityTraversalBehaviour() {
    }

    public static PathEntityTraversalBehaviour getInstance() {
        return INSTANCE;
    }

    public int advance(int currentIndex) {
        return currentIndex + 1;
    }

    public boolean isFinished(int currentIndex, PathPoint[] pathPoints) {
        return currentIndex >= pathPoints.length;
    }

    public PathPoint getLastPoint(PathPoint[] pathPoints, int pointCount) {
        return pointCount > 0 ? pathPoints[pointCount - 1] : null;
    }

    public Vec3D getCurrentPosition(PathPoint[] pathPoints, int currentIndex, Entity entity) {
        double x = (double) pathPoints[currentIndex].a + (double) ((int) (entity.length + 1.0F)) * 0.5D;
        double y = (double) pathPoints[currentIndex].b;
        double z = (double) pathPoints[currentIndex].c + (double) ((int) (entity.length + 1.0F)) * 0.5D;

        return Vec3D.create(x, y, z);
    }
}
