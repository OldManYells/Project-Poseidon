package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.GroundMovementResolutionSystem;
import org.junit.Assert;
import org.junit.Test;

public class GroundMovementResolutionServiceTest {
    @Test
    public void movedWronglyMessageHelpersPreserveLegacyTextShape() {
        GroundMovementResolutionSystem service = GroundMovementResolutionSystem.getInstance();

        Assert.assertEquals("Alex moved wrongly!", service.createMovedWronglyWarningMessage("Alex"));
        Assert.assertEquals("Got position 1.0, 2.0, 3.0", service.createMovedWronglyReceivedPosition(1.0D, 2.0D, 3.0D));
        Assert.assertEquals("Expected 4.0, 5.0, 6.0", service.createMovedWronglyExpectedPosition(4.0D, 5.0D, 6.0D));
    }

    @Test
    public void floatingKickMessageDelegatesToGuardService() {
        GroundMovementResolutionSystem service = GroundMovementResolutionSystem.getInstance();

        Assert.assertEquals("Alex was kicked for floating too long!", service.createFloatingKickLogMessage("Alex"));
    }
}
