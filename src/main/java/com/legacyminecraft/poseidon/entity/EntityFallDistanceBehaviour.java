package com.legacyminecraft.poseidon.entity;

/**
 * Canonical behaviour for fall-distance progression and landing application policy.
 */
public final class EntityFallDistanceBehaviour {
    private static final EntityFallDistanceBehaviour INSTANCE = new EntityFallDistanceBehaviour();

    private EntityFallDistanceBehaviour() {
    }

    public static EntityFallDistanceBehaviour getInstance() {
        return INSTANCE;
    }

    public FallDistanceUpdate update(double deltaY, boolean onGround, float currentFallDistance) {
        if (onGround) {
            if (currentFallDistance > 0.0F) {
                return FallDistanceUpdate.landed(currentFallDistance);
            }

            return FallDistanceUpdate.unchanged(currentFallDistance);
        }

        if (deltaY < 0.0D) {
            float nextFallDistance = (float) ((double) currentFallDistance - deltaY);
            return FallDistanceUpdate.unchanged(nextFallDistance);
        }

        return FallDistanceUpdate.unchanged(currentFallDistance);
    }

    public static final class FallDistanceUpdate {
        public final boolean shouldApplyLandingEffect;
        public final float landingDistance;
        public final float updatedFallDistance;

        private FallDistanceUpdate(boolean shouldApplyLandingEffect, float landingDistance, float updatedFallDistance) {
            this.shouldApplyLandingEffect = shouldApplyLandingEffect;
            this.landingDistance = landingDistance;
            this.updatedFallDistance = updatedFallDistance;
        }

        public static FallDistanceUpdate landed(float landingDistance) {
            return new FallDistanceUpdate(true, landingDistance, 0.0F);
        }

        public static FallDistanceUpdate unchanged(float updatedFallDistance) {
            return new FallDistanceUpdate(false, 0.0F, updatedFallDistance);
        }
    }
}
