package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.VehicleMovementPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class VehicleMovementPacketServiceTest {
    @Test
    public void crashResultFactorySetsCrashState() {
        VehicleMovementPacketHandler.VehicleMoveResult crash = VehicleMovementPacketHandler.VehicleMoveResult.crashAttempt();

        Assert.assertTrue(crash.isCrashAttempt());
        Assert.assertFalse(crash.isHandled());
    }

    @Test
    public void handledResultFactoryCarriesCoordinates() {
        VehicleMovementPacketHandler.VehicleMoveResult handled =
                VehicleMovementPacketHandler.VehicleMoveResult.handled(1.0D, 2.0D, 3.0D);

        Assert.assertFalse(handled.isCrashAttempt());
        Assert.assertTrue(handled.isHandled());
        Assert.assertEquals(1.0D, handled.getX(), 0.0D);
        Assert.assertEquals(2.0D, handled.getY(), 0.0D);
        Assert.assertEquals(3.0D, handled.getZ(), 0.0D);
    }

    @Test
    public void crashMessagesMatchLegacyBehavior() {
        VehicleMovementPacketHandler service = VehicleMovementPacketHandler.getInstance();

        Assert.assertEquals("Boat crash attempt detected!", service.getVehicleCrashKickMessage());
        Assert.assertTrue(service.createVehicleCrashLogMessage("Alex", "BoatEntity#12").contains("Alex"));
    }
}
