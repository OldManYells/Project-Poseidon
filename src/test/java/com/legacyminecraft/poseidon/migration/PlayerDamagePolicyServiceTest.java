package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerDamagePolicySystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerDamagePolicyServiceTest {
    @Test
    public void immunityTicksAlwaysBlockDamage() {
        PlayerDamagePolicySystem service = PlayerDamagePolicySystem.getInstance();

        Assert.assertFalse(service.shouldApplyDamage(1, true, false, false));
        Assert.assertFalse(service.shouldApplyDamage(10, false, true, true));
    }

    @Test
    public void pvpDisabledBlocksHumanAndHumanArrowDamage() {
        PlayerDamagePolicySystem service = PlayerDamagePolicySystem.getInstance();

        Assert.assertFalse(service.shouldApplyDamage(0, false, true, false));
        Assert.assertFalse(service.shouldApplyDamage(0, false, false, true));
    }

    @Test
    public void otherwiseDamageIsAllowed() {
        PlayerDamagePolicySystem service = PlayerDamagePolicySystem.getInstance();

        Assert.assertTrue(service.shouldApplyDamage(0, true, true, true));
        Assert.assertTrue(service.shouldApplyDamage(0, false, false, false));
    }
}
