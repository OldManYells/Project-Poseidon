package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.VehicleMoveOutcomeExecutionSystem;
import com.legacyminecraft.poseidon.network.VehicleMovementPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class VehicleMoveOutcomeExecutionSystemTest {
    private final VehicleMoveOutcomeExecutionSystem vehicleMoveOutcomeExecutionSystem = VehicleMoveOutcomeExecutionSystem.getInstance();
    private final VehicleMovementPacketHandler vehicleMovementPacketHandler = VehicleMovementPacketHandler.getInstance();

    @Test
    public void executeOutcomeCrashLogsAndKicksWithoutPositionUpdate() {
        VehicleMovementPacketHandler.VehicleMoveResult vehicleMoveResult =
                VehicleMovementPacketHandler.VehicleMoveResult.crashAttempt();
        VehicleMoveActionCapture capture = new VehicleMoveActionCapture();

        vehicleMoveOutcomeExecutionSystem.executeOutcome(
                vehicleMoveResult,
                vehicleMovementPacketHandler,
                "PlayerOne",
                "vehicle-object",
                capture
        );

        Assert.assertNotNull(capture.warningMessage);
        Assert.assertTrue(capture.warningMessage.contains("PlayerOne"));
        Assert.assertEquals("Boat crash attempt detected!", capture.kickMessage);
        Assert.assertFalse(capture.positionUpdated);
    }

    @Test
    public void executeOutcomeHandledUpdatesPositionOnly() {
        VehicleMovementPacketHandler.VehicleMoveResult vehicleMoveResult =
                VehicleMovementPacketHandler.VehicleMoveResult.handled(2.5D, 64.0D, -3.0D);
        VehicleMoveActionCapture capture = new VehicleMoveActionCapture();

        vehicleMoveOutcomeExecutionSystem.executeOutcome(
                vehicleMoveResult,
                vehicleMovementPacketHandler,
                "PlayerTwo",
                "vehicle-object",
                capture
        );

        Assert.assertNull(capture.warningMessage);
        Assert.assertNull(capture.kickMessage);
        Assert.assertTrue(capture.positionUpdated);
        Assert.assertEquals(2.5D, capture.x, 0.0001D);
        Assert.assertEquals(64.0D, capture.y, 0.0001D);
        Assert.assertEquals(-3.0D, capture.z, 0.0001D);
    }

    private static final class VehicleMoveActionCapture implements VehicleMoveOutcomeExecutionSystem.VehicleMoveActions {
        private String warningMessage;
        private String kickMessage;
        private boolean positionUpdated;
        private double x;
        private double y;
        private double z;

        @Override
        public void logCrashWarning(String warningMessage) {
            this.warningMessage = warningMessage;
        }

        @Override
        public void kickPlayer(String kickMessage) {
            this.kickMessage = kickMessage;
        }

        @Override
        public void updateLastKnownPosition(double x, double y, double z) {
            this.positionUpdated = true;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
