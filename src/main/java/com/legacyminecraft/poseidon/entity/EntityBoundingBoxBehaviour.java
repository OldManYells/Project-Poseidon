package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for applying entity position to axis-aligned bounds.
 */
public final class EntityBoundingBoxBehaviour {
    private static final EntityBoundingBoxBehaviour INSTANCE = new EntityBoundingBoxBehaviour();

    private EntityBoundingBoxBehaviour() {
    }

    public static EntityBoundingBoxBehaviour getInstance() {
        return INSTANCE;
    }

    public void updateBoundingBox(
            AxisAlignedBB boundingBox,
            double x,
            double y,
            double z,
            float length,
            float width,
            float height,
            float yOffset
    ) {
        float halfLength = length / 2.0F;
        double minY = y - (double) height + (double) yOffset;
        double maxY = minY + (double) width;
        boundingBox.c(
                x - (double) halfLength,
                minY,
                z - (double) halfLength,
                x + (double) halfLength,
                maxY,
                z + (double) halfLength
        );
    }
}
