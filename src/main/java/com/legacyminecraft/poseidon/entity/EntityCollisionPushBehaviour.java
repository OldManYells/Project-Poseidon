package com.legacyminecraft.poseidon.entity;

import net.minecraft.server.MathHelper;

/**
 * Canonical behaviour for entity horizontal collision push-vector calculation.
 */
public final class EntityCollisionPushBehaviour {
    private static final EntityCollisionPushBehaviour INSTANCE = new EntityCollisionPushBehaviour();

    private EntityCollisionPushBehaviour() {
    }

    public static EntityCollisionPushBehaviour getInstance() {
        return INSTANCE;
    }

    public PushVector computeHorizontalPush(double sourceX, double sourceZ, double targetX, double targetZ, float collisionReduction) {
        double deltaX = targetX - sourceX;
        double deltaZ = targetZ - sourceZ;
        double distanceSquared = MathHelper.a(deltaX, deltaZ);
        if (distanceSquared < 0.009999999776482582D) {
            return PushVector.zero();
        }

        double distance = (double) MathHelper.a(distanceSquared);
        deltaX /= distance;
        deltaZ /= distance;
        double inverseDistance = 1.0D / distance;
        if (inverseDistance > 1.0D) {
            inverseDistance = 1.0D;
        }

        double scale = 0.05000000074505806D * inverseDistance * (double) (1.0F - collisionReduction);
        return new PushVector(deltaX * scale, deltaZ * scale);
    }

    public static final class PushVector {
        private static final PushVector ZERO = new PushVector(0.0D, 0.0D);

        public final double x;
        public final double z;

        public PushVector(double x, double z) {
            this.x = x;
            this.z = z;
        }

        public static PushVector zero() {
            return ZERO;
        }

        public boolean isZero() {
            return this.x == 0.0D && this.z == 0.0D;
        }
    }
}
