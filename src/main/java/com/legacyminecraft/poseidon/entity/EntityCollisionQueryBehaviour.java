package com.legacyminecraft.poseidon.entity;


import java.util.List;

/**
 * Canonical behaviour for entity movement collision-space queries.
 */
public final class EntityCollisionQueryBehaviour {
    private static final EntityCollisionQueryBehaviour INSTANCE = new EntityCollisionQueryBehaviour();

    private EntityCollisionQueryBehaviour() {
    }

    public static EntityCollisionQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canMove(World world, Entity entity, AxisAlignedBB boundingBox, double moveX, double moveY, double moveZ) {
        AxisAlignedBB movedBoundingBox = boundingBox.c(moveX, moveY, moveZ);
        List collidingEntities = world.getEntities(entity, movedBoundingBox);
        boolean hasEntityCollision = !collidingEntities.isEmpty();
        boolean hasBlockCollision = world.c(movedBoundingBox);
        return !hasEntityCollision && !hasBlockCollision;
    }
}
