package com.legacyminecraft.poseidon.network;


/**
 * Canonical helper for legacy teleport execution packet/state planning.
 */
public final class PlayerTeleportExecutionSystem {
    private static final PlayerTeleportExecutionSystem INSTANCE = new PlayerTeleportExecutionSystem();

    private final PlayerTeleportCoordinator teleportCoordinator = PlayerTeleportCoordinator.getInstance();
    private final PlayerTeleportHeightPolicy playerTeleportHeightPolicy = PlayerTeleportHeightPolicy.getInstance();

    private PlayerTeleportExecutionSystem() {
    }

    public static PlayerTeleportExecutionSystem getInstance() {
        return INSTANCE;
    }

    public TeleportExecutionPlan createExecutionPlan(Location destination) {
        PlayerTeleportCoordinator.TeleportPlan plan = teleportCoordinator.createTeleportPlan(destination);
        return new TeleportExecutionPlan(
                plan.getX(),
                plan.getY(),
                plan.getZ(),
                plan.getYaw(),
                plan.getPitch(),
                true,
                false,
                createLookMovePacket(plan.getX(), plan.getY(), plan.getZ(), plan.getYaw(), plan.getPitch())
        );
    }

    public TeleportExecutionPlan createExecutionPlan(Object destination) {
        return createExecutionPlan((Location) destination);
    }

    public Packet13PlayerLookMove createLookMovePacket(Location location) {
        return createLookMovePacket(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    public Packet13PlayerLookMove createLookMovePacket(Object location) {
        return createLookMovePacket((Location) location);
    }

    public Packet13PlayerLookMove createLookMovePacket(double x, double y, double z, float yaw, float pitch) {
        return new Packet13PlayerLookMove(
                x,
                y + playerTeleportHeightPolicy.legacyEyeHeightOffset(),
                y,
                z,
                yaw,
                pitch,
                false
        );
    }

    public static final class TeleportExecutionPlan {
        private final double x;
        private final double y;
        private final double z;
        private final float yaw;
        private final float pitch;
        private final boolean justTeleported;
        private final boolean movementCheckEnabled;
        private final Packet13PlayerLookMove teleportPacket;

        private TeleportExecutionPlan(
                double x,
                double y,
                double z,
                float yaw,
                float pitch,
                boolean justTeleported,
                boolean movementCheckEnabled,
                Packet13PlayerLookMove teleportPacket
        ) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.justTeleported = justTeleported;
            this.movementCheckEnabled = movementCheckEnabled;
            this.teleportPacket = teleportPacket;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getZ() {
            return z;
        }

        public float getYaw() {
            return yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public boolean isJustTeleported() {
            return justTeleported;
        }

        public boolean isMovementCheckEnabled() {
            return movementCheckEnabled;
        }

        public Packet13PlayerLookMove getTeleportPacket() {
            return teleportPacket;
        }
    }
}
