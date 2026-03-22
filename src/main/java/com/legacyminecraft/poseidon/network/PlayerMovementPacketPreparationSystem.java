package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet10Flying;

/**
 * Canonical translator/validator for converting movement packets into concrete movement targets.
 */
public final class PlayerMovementPacketPreparationSystem {
    private static final PlayerMovementPacketPreparationSystem INSTANCE = new PlayerMovementPacketPreparationSystem();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    private PlayerMovementPacketPreparationSystem() {
    }

    public static PlayerMovementPacketPreparationSystem getInstance() {
        return INSTANCE;
    }

    public MovementPreparationResult prepareForPlayer(EntityPlayer player, Packet10Flying packet10flying) {
        return prepare(
                player.isSleeping(),
                player.locX,
                player.locY,
                player.locZ,
                player.yaw,
                player.pitch,
                packet10flying.h,
                packet10flying.x,
                packet10flying.y,
                packet10flying.z,
                packet10flying.stance,
                packet10flying.hasLook,
                packet10flying.yaw,
                packet10flying.pitch
        );
    }

    public MovementPreparationResult prepare(
            boolean sleeping,
            double currentX,
            double currentY,
            double currentZ,
            float currentYaw,
            float currentPitch,
            boolean packetHasPosition,
            double packetX,
            double packetY,
            double packetZ,
            double packetStance,
            boolean packetHasLook,
            float packetYaw,
            float packetPitch
    ) {
        double targetX = currentX;
        double targetY = currentY;
        double targetZ = currentZ;
        float targetYaw = currentYaw;
        float targetPitch = currentPitch;

        boolean hasConcretePosition = packetHasPosition && !(packetY == -999.0D && packetStance == -999.0D);
        if (hasConcretePosition) {
            targetX = packetX;
            targetY = packetY;
            targetZ = packetZ;

            double stanceDelta = packetStance - packetY;
            if (movementPacketPolicy.isIllegalStance(sleeping, stanceDelta)) {
                return MovementPreparationResult.illegalStance(stanceDelta);
            }

            if (movementPacketPolicy.isIllegalHorizontalPosition(packetX, packetZ)) {
                return MovementPreparationResult.illegalPosition();
            }
        }

        if (packetHasLook) {
            targetYaw = packetYaw;
            targetPitch = packetPitch;
        }

        return MovementPreparationResult.valid(targetX, targetY, targetZ, targetYaw, targetPitch);
    }

    public static final class MovementPreparationResult {
        private final boolean valid;
        private final boolean illegalStance;
        private final double illegalStanceDelta;
        private final String disconnectReason;
        private final double targetX;
        private final double targetY;
        private final double targetZ;
        private final float targetYaw;
        private final float targetPitch;

        private MovementPreparationResult(
                boolean valid,
                boolean illegalStance,
                double illegalStanceDelta,
                String disconnectReason,
                double targetX,
                double targetY,
                double targetZ,
                float targetYaw,
                float targetPitch
        ) {
            this.valid = valid;
            this.illegalStance = illegalStance;
            this.illegalStanceDelta = illegalStanceDelta;
            this.disconnectReason = disconnectReason;
            this.targetX = targetX;
            this.targetY = targetY;
            this.targetZ = targetZ;
            this.targetYaw = targetYaw;
            this.targetPitch = targetPitch;
        }

        public static MovementPreparationResult valid(double targetX, double targetY, double targetZ, float targetYaw, float targetPitch) {
            return new MovementPreparationResult(true, false, 0.0D, null, targetX, targetY, targetZ, targetYaw, targetPitch);
        }

        public static MovementPreparationResult illegalStance(double stanceDelta) {
            return new MovementPreparationResult(false, true, stanceDelta, "Illegal stance", 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        }

        public static MovementPreparationResult illegalPosition() {
            return new MovementPreparationResult(false, false, 0.0D, "Illegal position", 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        }

        public boolean isValid() {
            return valid;
        }

        public boolean isIllegalStance() {
            return illegalStance;
        }

        public double getIllegalStanceDelta() {
            return illegalStanceDelta;
        }

        public String getDisconnectReason() {
            return disconnectReason;
        }

        public double getTargetX() {
            return targetX;
        }

        public double getTargetY() {
            return targetY;
        }

        public double getTargetZ() {
            return targetZ;
        }

        public float getTargetYaw() {
            return targetYaw;
        }

        public float getTargetPitch() {
            return targetPitch;
        }
    }
}
