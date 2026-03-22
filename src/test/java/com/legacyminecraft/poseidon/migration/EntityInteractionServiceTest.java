package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.EntityInteractionSystem;
import org.junit.Assert;
import org.junit.Test;

public class EntityInteractionServiceTest {
    @Test
    public void minecartGuardOnlyCancelsVehicleStorageMinecartCase() {
        EntityInteractionSystem service = EntityInteractionSystem.getInstance();

        Assert.assertTrue(service.shouldCancelStorageMinecartInteraction(true, true));
        Assert.assertFalse(service.shouldCancelStorageMinecartInteraction(true, false));
        Assert.assertFalse(service.shouldCancelStorageMinecartInteraction(false, true));
    }
}
