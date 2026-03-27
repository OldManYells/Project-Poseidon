package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.entity.CombatFixConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class CombatFixConfigPolicyTest {
    @Test
    public void exposesCombatFixKeysAndDefaults() {
        CombatFixConfigPolicy policy = CombatFixConfigPolicy.getInstance();

        Assert.assertEquals("settings.fix-drowning-push-down.enabled", policy.drowningPushDownFixKey());
        Assert.assertTrue(policy.drowningPushDownFixDefault());
        Assert.assertEquals("settings.player-knockback-fix.enabled", policy.playerKnockbackFixKey());
        Assert.assertTrue(policy.playerKnockbackFixDefault());
        Assert.assertEquals("world.settings.skeleton-shooting-sound-fix.enabled", policy.skeletonShootingSoundFixKey());
        Assert.assertTrue(policy.skeletonShootingSoundFixDefault());
    }
}
