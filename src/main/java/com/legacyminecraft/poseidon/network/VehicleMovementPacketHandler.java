package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet10Flying;
import net.minecraft.server.ServerConfigurationManager;
import net.minecraft.server.WorldServer;

/**
 * Canonical coordinator for processing movement packets while a player is riding a vehicle.
 */
public final class VehicleMovementPacketHandler {
    private static final VehicleMovementPacketHandler INSTANCE = new VehicleMovementPacketHandler();
    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    private VehicleMovementPacketHandler() {
    }

    public static VehicleMovementPacketHandler getInstance() {
        return INSTANCE;
    }

    public VehicleMoveResult handleVehicleMovement(
            EntityPlayer player,
            WorldServer worldServer,
            ServerConfigurationManager serverConfigurationManager,
            Packet10Flying packet10flying
    ) {
        float yaw = player.yaw;
        float pitch = player.pitch;

        player.vehicle.f();
        double anchorX = player.locX;
        double anchorY = player.locY;
        double anchorZ = player.locZ;

        double moveX = 0.0D;
        double moveZ = 0.0D;
        if (packet10flying.hasLook) {
            yaw = packet10flying.yaw;
            pitch = packet10flying.pitch;
        }

        if (packet10flying.h && packet10flying.y == -999.0D && packet10flying.stance == -999.0D) {
            moveX = packet10flying.x;
            moveZ = packet10flying.z;

            if (movementPacketPolicy.isVehicleCrashAttempt(moveX, moveZ)) {
                return VehicleMoveResult.crashAttempt();
            }
        }

        player.onGround = packet10flying.g;
        player.a(true);
        player.move(moveX, 0.0D, moveZ);
        player.setLocation(anchorX, anchorY, anchorZ, yaw, pitch);
        player.motX = moveX;
        player.motZ = moveZ;

        if (player.vehicle != null) {
            worldServer.vehicleEnteredWorld(player.vehicle, true);
        }

        if (player.vehicle != null) {
            player.vehicle.f();
            player.vehicle.airBorne = true;
        }

        serverConfigurationManager.d(player);
        worldServer.playerJoinedWorld(player);
        return VehicleMoveResult.handled(player.locX, player.locY, player.locZ);
    }

    public String createVehicleCrashLogMessage(String playerName, Object vehicle) {
        return "[Poseidon]" + playerName + " tried crashing server on entity " + vehicle + ". They have been kicked.";
    }

    public String getVehicleCrashKickMessage() {
        return "Boat crash attempt detected!";
    }

    public static final class VehicleMoveResult {
        private final boolean handled;
        private final boolean crashAttempt;
        private final double x;
        private final double y;
        private final double z;

        private VehicleMoveResult(boolean handled, boolean crashAttempt, double x, double y, double z) {
            this.handled = handled;
            this.crashAttempt = crashAttempt;
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public static VehicleMoveResult handled(double x, double y, double z) {
            return new VehicleMoveResult(true, false, x, y, z);
        }

        public static VehicleMoveResult crashAttempt() {
            return new VehicleMoveResult(false, true, 0.0D, 0.0D, 0.0D);
        }

        public boolean isHandled() {
            return handled;
        }

        public boolean isCrashAttempt() {
            return crashAttempt;
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
    }
}
