package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for entity spatial calculations and orientation wrapping.
 */
public final class EntitySpatialBehaviour {
    private static final EntitySpatialBehaviour INSTANCE = new EntitySpatialBehaviour();

    private EntitySpatialBehaviour() {
    }

    public static EntitySpatialBehaviour getInstance() {
        return INSTANCE;
    }

    public float wrapPreviousYaw(float previousYaw, float currentYaw) {
        double yawDelta = (double) (previousYaw - currentYaw);
        if (yawDelta < -180.0D) {
            return previousYaw + 360.0F;
        }

        if (yawDelta >= 180.0D) {
            return previousYaw - 360.0F;
        }

        return previousYaw;
    }

    public double elevatedY(double y, float height) {
        return y + (double) height;
    }

    public double distanceSquared(double x1, double y1, double z1, double x2, double y2, double z2) {
        double deltaX = x1 - x2;
        double deltaY = y1 - y2;
        double deltaZ = z1 - z2;
        return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
    }

    public double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return (double) MathHelper.a(this.distanceSquared(x1, y1, z1, x2, y2, z2));
    }

    public float distanceToEntity(double x1, double y1, double z1, double x2, double y2, double z2) {
        return MathHelper.c((float) this.distanceSquared(x1, y1, z1, x2, y2, z2));
    }
}
