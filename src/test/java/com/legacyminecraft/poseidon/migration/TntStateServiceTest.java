package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.TntStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class TntStateServiceTest {
    @Test
    public void texturePowerAndBreakRulesMatchLegacyTntBehavior() {
        TntStateBehaviour service = TntStateBehaviour.getInstance();

        Assert.assertEquals(12, service.resolveTextureBySide(0, 10));
        Assert.assertEquals(11, service.resolveTextureBySide(1, 10));
        Assert.assertEquals(10, service.resolveTextureBySide(2, 10));
        Assert.assertTrue(service.shouldPrimeOnPlacement(true));
        Assert.assertFalse(service.shouldPrimeOnPlacement(false));
        Assert.assertTrue(service.shouldPrimeOnPhysics(1, true, true));
        Assert.assertFalse(service.shouldPrimeOnPhysics(0, true, true));
        Assert.assertFalse(service.shouldPrimeOnPhysics(1, false, true));
        Assert.assertFalse(service.shouldPrimeOnPhysics(1, true, false));
        Assert.assertTrue(service.shouldDropAsItem(0));
        Assert.assertFalse(service.shouldDropAsItem(1));
        Assert.assertTrue(service.shouldPrimeOnPostBreak(1));
        Assert.assertFalse(service.shouldPrimeOnPostBreak(0));
        Assert.assertTrue(service.shouldMarkIgnitedFromHeldItem(259, 259));
        Assert.assertFalse(service.shouldMarkIgnitedFromHeldItem(1, 259));
    }

    @Test
    public void coordinateAndFuseRulesMatchLegacyTntBehavior() {
        TntStateBehaviour service = TntStateBehaviour.getInstance();

        Assert.assertEquals(10.5D, service.resolveCenteredSpawnCoordinate(10), 0.0D);
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        Assert.assertEquals(10, service.resolveDispensedFuseTicks(deterministic, 80));
    }
}
