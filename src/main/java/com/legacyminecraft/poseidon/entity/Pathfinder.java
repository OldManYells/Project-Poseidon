package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local pathfinder scaffold.
 */
public class Pathfinder {
    private final World world;

    public Pathfinder(World world) {
        this.world = world;
    }

    public PathEntity a(EntityLiving source, Entity target, float range) {
        if (source == null || target == null) {
            return null;
        }

        PathPoint sourcePoint = new PathPoint(MathHelper.floor(source.locX), MathHelper.floor(source.locY), MathHelper.floor(source.locZ));
        PathPoint targetPoint = new PathPoint(MathHelper.floor(target.locX), MathHelper.floor(target.locY), MathHelper.floor(target.locZ));
        return new PathEntity(new PathPoint[]{sourcePoint, targetPoint});
    }

    public World getWorld() {
        return world;
    }
}
