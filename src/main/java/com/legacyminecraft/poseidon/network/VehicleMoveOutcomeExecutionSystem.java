package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution flow for processed vehicle movement outcomes.
 */
public final class VehicleMoveOutcomeExecutionSystem {
    private static final VehicleMoveOutcomeExecutionSystem INSTANCE = new VehicleMoveOutcomeExecutionSystem();

    private VehicleMoveOutcomeExecutionSystem() {
    }

    public static VehicleMoveOutcomeExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void executeOutcome(
            VehicleMovementPacketHandler.VehicleMoveResult vehicleMoveResult,
            VehicleMovementPacketHandler vehicleMovementPacketHandler,
            String playerName,
            Object playerVehicle,
            VehicleMoveActions vehicleMoveActions
    ) {
        if (vehicleMoveResult.isCrashAttempt()) {
            vehicleMoveActions.logCrashWarning(
                    vehicleMovementPacketHandler.createVehicleCrashLogMessage(playerName, playerVehicle)
            );
            vehicleMoveActions.kickPlayer(vehicleMovementPacketHandler.getVehicleCrashKickMessage());
            return;
        }

        if (vehicleMoveResult.isHandled()) {
            vehicleMoveActions.updateLastKnownPosition(
                    vehicleMoveResult.getX(),
                    vehicleMoveResult.getY(),
                    vehicleMoveResult.getZ()
            );
        }
    }

    public interface VehicleMoveActions {
        void logCrashWarning(String warningMessage);

        void kickPlayer(String kickMessage);

        void updateLastKnownPosition(double x, double y, double z);
    }
}
