package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementSpeedConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class MovementSpeedConfigPolicyTest {
    @Test
    public void exposesSpeedCheckConfigKeysAndDefaults() {
        MovementSpeedConfigPolicy policy = MovementSpeedConfigPolicy.getInstance();

        Assert.assertEquals("world.settings.speed-hack-check.enabled", policy.speedCheckEnabledKey());
        Assert.assertTrue(policy.speedCheckEnabledDefault());
        Assert.assertEquals("world.settings.speed-hack-check.distance", policy.speedCheckDistanceKey());
        Assert.assertEquals(100.0D, policy.speedCheckDistanceDefault(), 0.0D);
        Assert.assertEquals("world.settings.speed-hack-check.teleport", policy.speedCheckTeleportKey());
        Assert.assertTrue(policy.speedCheckTeleportDefault());
    }
}
