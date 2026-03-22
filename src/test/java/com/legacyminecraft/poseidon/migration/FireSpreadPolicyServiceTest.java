package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FireSpreadBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class FireSpreadPolicyServiceTest {
    @Test
    public void flammabilityAgeAndPlacementRulesMatchLegacyBehavior() {
        FireSpreadBehaviour service = FireSpreadBehaviour.getInstance();
        int[] encouragement = new int[256];
        int[] burnOdds = new int[256];

        service.setFlammability(encouragement, burnOdds, 5, 30, 20);
        Assert.assertEquals(30, service.resolveEncouragement(encouragement, 5));
        Assert.assertEquals(20, service.resolveNeighborBurnOdds(burnOdds, 5));
        Assert.assertEquals(0, service.resolveDropCount());
        Assert.assertEquals(40, service.resolveTickRate());
        Assert.assertTrue(service.isEternalBase(87, 87));
        Assert.assertFalse(service.isEternalBase(1, 87));
        Assert.assertTrue(service.shouldRemoveForInvalidPlacement(false));
        Assert.assertFalse(service.shouldRemoveForInvalidPlacement(true));
        Assert.assertTrue(service.shouldRemoveForRain(false, true, true));
        Assert.assertFalse(service.shouldRemoveForRain(true, true, true));
        Assert.assertEquals(5, service.nextFireAge(5, 0));
        Assert.assertEquals(6, service.nextFireAge(5, 2));
        Assert.assertEquals(15, service.nextFireAge(15, 2));
        Assert.assertTrue(service.shouldRemoveWithoutSupport(false, false, false, 4));
        Assert.assertFalse(service.shouldRemoveWithoutSupport(false, true, false, 10));
        Assert.assertTrue(service.shouldRemoveAtMaxAge(false, false, 15, 0));
        Assert.assertFalse(service.shouldRemoveAtMaxAge(false, false, 15, 1));
        Assert.assertTrue(service.shouldAttemptNeighborBurn(2, 5));
        Assert.assertFalse(service.shouldAttemptNeighborBurn(5, 5));
        Assert.assertTrue(service.shouldIgniteBurnedBlock(4, false));
        Assert.assertFalse(service.shouldIgniteBurnedBlock(5, false));
        Assert.assertEquals(15, service.nextSpreadAge(15, 4));
        Assert.assertEquals(7, service.nextSpreadAge(6, 4));
        Assert.assertEquals(300, service.resolveVerticalSpreadBound(10, 13, 100));
        Assert.assertEquals(2, service.resolveSpreadChanceFromNeighbor(20, 0));
        Assert.assertTrue(service.shouldSpreadToAir(2, 2, false, false));
        Assert.assertFalse(service.shouldSpreadToAir(2, 3, false, false));
        Assert.assertTrue(service.canPlace(true, false));
        Assert.assertTrue(service.canPlace(false, true));
        Assert.assertFalse(service.canPlace(false, false));
        Assert.assertTrue(service.shouldDropOnPhysics(false, false));
        Assert.assertFalse(service.shouldDropOnPhysics(true, false));
        Assert.assertTrue(service.shouldTryPortalCreation(49, 49));
        Assert.assertFalse(service.shouldTryPortalCreation(1, 49));
    }

    @Test
    public void neighborBurnabilityAndEncouragementQueriesMatchLegacyBehavior() {
        FireSpreadBehaviour service = FireSpreadBehaviour.getInstance();

        Assert.assertTrue(service.hasBurnableNeighbor(new FireSpreadBehaviour.BurnableQuery() {
            public boolean isBurnable(int x, int y, int z) {
                return x == 11 && y == 64 && z == 10;
            }
        }, 10, 64, 10));

        Assert.assertFalse(service.hasBurnableNeighbor(new FireSpreadBehaviour.BurnableQuery() {
            public boolean isBurnable(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10));

        Assert.assertEquals(30, service.resolveNeighborEncouragement(new FireSpreadBehaviour.MaxEncouragementQuery() {
            public boolean isEmpty(int x, int y, int z) {
                return true;
            }

            public int encouragementAt(int x, int y, int z) {
                return x == 11 && y == 64 && z == 10 ? 30 : 5;
            }
        }, 10, 64, 10));

        Assert.assertEquals(0, service.resolveNeighborEncouragement(new FireSpreadBehaviour.MaxEncouragementQuery() {
            public boolean isEmpty(int x, int y, int z) {
                return false;
            }

            public int encouragementAt(int x, int y, int z) {
                return 30;
            }
        }, 10, 64, 10));

        Assert.assertEquals(7, service.maxEncouragement(7, 3));
        Assert.assertEquals(9, service.maxEncouragement(5, 9));
        Assert.assertTrue(service.isBurnable(1));
        Assert.assertFalse(service.isBurnable(0));
    }
}
