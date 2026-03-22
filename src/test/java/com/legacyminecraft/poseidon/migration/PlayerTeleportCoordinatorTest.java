package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerTeleportCoordinator;
import org.bukkit.Location;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTeleportCoordinatorTest {
    @Test
    public void sanitizeRotationReplacesNaNWithZero() {
        PlayerTeleportCoordinator coordinator = PlayerTeleportCoordinator.getInstance();

        Assert.assertEquals(0.0F, coordinator.sanitizeRotationComponent(Float.NaN), 0.0F);
        Assert.assertEquals(45.0F, coordinator.sanitizeRotationComponent(45.0F), 0.0F);
    }

    @Test
    public void teleportPlanSanitizesYawPitch() {
        PlayerTeleportCoordinator coordinator = PlayerTeleportCoordinator.getInstance();
        Location location = new Location(null, 1.0D, 2.0D, 3.0D, Float.NaN, Float.NaN);

        PlayerTeleportCoordinator.TeleportPlan plan = coordinator.createTeleportPlan(location);
        Assert.assertEquals(1.0D, plan.getX(), 0.0D);
        Assert.assertEquals(2.0D, plan.getY(), 0.0D);
        Assert.assertEquals(3.0D, plan.getZ(), 0.0D);
        Assert.assertEquals(0.0F, plan.getYaw(), 0.0F);
        Assert.assertEquals(0.0F, plan.getPitch(), 0.0F);
    }
}
