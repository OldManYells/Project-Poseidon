package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local path container scaffold.
 */
public class PathEntity {
    public final int a;
    private final PathPoint[] points;

    public PathEntity(PathPoint[] points) {
        this.points = points == null ? new PathPoint[0] : points;
        this.a = this.points.length;
    }

    public PathPoint c() {
        return points.length == 0 ? null : points[points.length - 1];
    }
}
