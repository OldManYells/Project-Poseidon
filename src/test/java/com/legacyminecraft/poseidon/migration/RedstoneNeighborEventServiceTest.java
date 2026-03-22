package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.RedstoneNeighborEventBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class RedstoneNeighborEventServiceTest {
    @Test
    public void neighborPowerSourceGatingMatchesLegacyBehavior() {
        RedstoneNeighborEventBehaviour service = RedstoneNeighborEventBehaviour.getInstance();

        Assert.assertTrue(service.shouldFireNeighborPowerEvent(true, true));
        Assert.assertFalse(service.shouldFireNeighborPowerEvent(false, true));
        Assert.assertFalse(service.shouldFireNeighborPowerEvent(true, false));
    }
}
