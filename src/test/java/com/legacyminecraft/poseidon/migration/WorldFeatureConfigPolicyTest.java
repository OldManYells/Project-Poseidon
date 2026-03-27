package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class WorldFeatureConfigPolicyTest {
    private final WorldFeatureConfigPolicy policy = WorldFeatureConfigPolicy.getInstance();

    @Test
    public void exposesExpectedWorldFeatureConfigKeysAndDefaults() {
        Assert.assertEquals("world.settings.flowing-lava-fix.enabled", policy.flowingLavaFixEnabledKey());
        Assert.assertTrue(policy.flowingLavaFixEnabledDefault());

        Assert.assertEquals("world.settings.pistons.sand-gravel-duping-fix.enabled", policy.pistonSandGravelDupingFixEnabledKey());
        Assert.assertTrue(policy.pistonSandGravelDupingFixEnabledDefault());

        Assert.assertEquals("world.settings.pistons.other-fixes.enabled", policy.pistonOtherFixesEnabledKey());
        Assert.assertTrue(policy.pistonOtherFixesEnabledDefault());

        Assert.assertEquals("world.settings.pistons.transmutation-fix.enabled", policy.pistonTransmutationFixEnabledKey());
        Assert.assertTrue(policy.pistonTransmutationFixEnabledDefault());

        Assert.assertEquals("world-settings.optimized-explosions", policy.optimizedExplosionsKey());
        Assert.assertFalse(policy.optimizedExplosionsDefault());

        Assert.assertEquals("world-settings.send-explosion-velocity", policy.sendExplosionVelocityKey());
        Assert.assertTrue(policy.sendExplosionVelocityDefault());

        Assert.assertEquals("world-settings.randomize-spawn", policy.randomizeSpawnKey());
        Assert.assertTrue(policy.randomizeSpawnDefault());

        Assert.assertEquals("fix.optimize-sponges.enabled", policy.optimizeSpongeRemovalKey());
        Assert.assertTrue(policy.optimizeSpongeRemovalDefault());

        Assert.assertEquals("world.settings.mob-spawner-area-limit.enable", policy.mobSpawnerAreaLimitEnabledKey());
        Assert.assertTrue(policy.mobSpawnerAreaLimitEnabledDefault());

        Assert.assertEquals("world.settings.mob-spawner-area-limit.limit", policy.mobSpawnerAreaLimitKey());
        Assert.assertEquals(150, policy.mobSpawnerAreaLimitDefault());

        Assert.assertEquals("world.settings.mob-spawner-area-limit.chunk-radius", policy.mobSpawnerChunkRadiusKey());
        Assert.assertEquals(8, policy.mobSpawnerChunkRadiusDefault());

        Assert.assertEquals("emergency.debug.regenerate-corrupt-chunks.enable", policy.regenerateCorruptChunksEnabledKey());
        Assert.assertFalse(policy.regenerateCorruptChunksEnabledDefault());
    }
}
