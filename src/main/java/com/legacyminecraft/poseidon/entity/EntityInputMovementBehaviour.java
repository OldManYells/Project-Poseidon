package com.legacyminecraft.poseidon.entity;


/**
 * Canonical behaviour for translating movement input into horizontal motion deltas.
 */
public final class EntityInputMovementBehaviour {
    private static final EntityInputMovementBehaviour INSTANCE = new EntityInputMovementBehaviour();

    private EntityInputMovementBehaviour() {
    }

    public static EntityInputMovementBehaviour getInstance() {
        return INSTANCE;
    }

    public MotionDelta computeMotionDelta(float inputX, float inputZ, float speed, float yawDegrees) {
        float magnitude = MathHelper.c(inputX * inputX + inputZ * inputZ);
        if (magnitude < 0.01F) {
            return MotionDelta.ZERO;
        }

        if (magnitude < 1.0F) {
            magnitude = 1.0F;
        }

        float normalizedSpeed = speed / magnitude;
        inputX *= normalizedSpeed;
        inputZ *= normalizedSpeed;
        float yawSin = MathHelper.sin(yawDegrees * 3.1415927F / 180.0F);
        float yawCos = MathHelper.cos(yawDegrees * 3.1415927F / 180.0F);
        double deltaX = (double) (inputX * yawCos - inputZ * yawSin);
        double deltaZ = (double) (inputZ * yawCos + inputX * yawSin);
        return new MotionDelta(deltaX, deltaZ);
    }

    public static final class MotionDelta {
        public static final MotionDelta ZERO = new MotionDelta(0.0D, 0.0D);

        public final double x;
        public final double z;

        public MotionDelta(double x, double z) {
            this.x = x;
            this.z = z;
        }

        public boolean isZero() {
            return this.x == 0.0D && this.z == 0.0D;
        }
    }
}
