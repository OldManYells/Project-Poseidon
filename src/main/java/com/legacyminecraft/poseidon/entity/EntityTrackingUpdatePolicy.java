package com.legacyminecraft.poseidon.entity;


/**
 * Canonical policy for encoding and comparing tracked entity movement updates.
 */
public final class EntityTrackingUpdatePolicy {
    private static final EntityTrackingUpdatePolicy INSTANCE = new EntityTrackingUpdatePolicy();
    private static final int MOVEMENT_UPDATE_THRESHOLD = 1;
    private static final int ROTATION_UPDATE_THRESHOLD = 1;

    private EntityTrackingUpdatePolicy() {
    }

    public static EntityTrackingUpdatePolicy getInstance() {
        return INSTANCE;
    }

    public EncodedEntityState encode(Entity tracker) {
        int x = MathHelper.floor(tracker.locX * 32.0D);
        int y = MathHelper.floor(tracker.locY * 32.0D);
        int z = MathHelper.floor(tracker.locZ * 32.0D);
        int yaw = MathHelper.d(tracker.yaw * 256.0F / 360.0F);
        int pitch = MathHelper.d(tracker.pitch * 256.0F / 360.0F);
        return new EncodedEntityState(x, y, z, yaw, pitch);
    }

    public boolean needsPositionUpdate(Entity tracker, int diffX, int diffY, int diffZ) {
        return Math.abs(diffX) >= MOVEMENT_UPDATE_THRESHOLD
                || Math.abs(diffY) >= MOVEMENT_UPDATE_THRESHOLD
                || Math.abs(diffZ) >= MOVEMENT_UPDATE_THRESHOLD
                || tracker instanceof EntityBoat
                || tracker instanceof EntityMinecart;
    }

    public boolean needsRotationUpdate(int newYaw, int newPitch, int oldYaw, int oldPitch) {
        return Math.abs(newYaw - oldYaw) >= ROTATION_UPDATE_THRESHOLD
                || Math.abs(newPitch - oldPitch) >= ROTATION_UPDATE_THRESHOLD;
    }

    public boolean canUseRelativePacket(int diffX, int diffY, int diffZ, int teleportCounter) {
        return diffX >= -128
                && diffX < 128
                && diffY >= -128
                && diffY < 128
                && diffZ >= -128
                && diffZ < 128
                && teleportCounter <= 400;
    }

    public boolean shouldSendVelocityUpdate(Entity tracker, double previousMotionX, double previousMotionY, double previousMotionZ) {
        double diffX = tracker.motX - previousMotionX;
        double diffY = tracker.motY - previousMotionY;
        double diffZ = tracker.motZ - previousMotionZ;
        double threshold = 0.02D;
        double diffMagnitudeSquared = diffX * diffX + diffY * diffY + diffZ * diffZ;

        return diffMagnitudeSquared > threshold * threshold
                || (diffMagnitudeSquared > 0.0D
                && tracker.motX == 0.0D
                && tracker.motY == 0.0D
                && tracker.motZ == 0.0D);
    }

    public static final class EncodedEntityState {
        private final int x;
        private final int y;
        private final int z;
        private final int yaw;
        private final int pitch;

        private EncodedEntityState(int x, int y, int z, int yaw, int pitch) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
        }

        public int getYaw() {
            return yaw;
        }

        public int getPitch() {
            return pitch;
        }
    }
}
