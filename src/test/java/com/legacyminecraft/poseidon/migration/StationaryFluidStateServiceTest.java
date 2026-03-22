package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.StationaryFluidStateBehaviour;
import net.minecraft.server.Material;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class StationaryFluidStateServiceTest {
    @Test
    public void flowingTransitionAndLavaIgnitionRulesMatchLegacyBehavior() {
        StationaryFluidStateBehaviour service = StationaryFluidStateBehaviour.getInstance();

        Assert.assertEquals(10, service.resolveFlowingBlockId(11));
        Assert.assertTrue(service.shouldConvertToFlowing(11, 11));
        Assert.assertFalse(service.shouldConvertToFlowing(10, 11));
        Assert.assertTrue(service.shouldAttemptLavaIgnition(Material.LAVA));
        Assert.assertFalse(service.shouldAttemptLavaIgnition(Material.WATER));
        Assert.assertTrue(service.isAirBlock(0));
        Assert.assertFalse(service.isAirBlock(1));
        Assert.assertTrue(service.shouldStopAtSolid(true));
        Assert.assertFalse(service.shouldStopAtSolid(false));
    }

    @Test
    public void ignitionOffsetAndNeighborBurnabilityRulesMatchLegacyBehavior() {
        StationaryFluidStateBehaviour service = StationaryFluidStateBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertEquals(0, service.resolveIgnitionAttempts(deterministic));
        Assert.assertEquals(-1, service.resolveHorizontalOffset(deterministic));

        Assert.assertTrue(service.hasBurnableNeighbor(new StationaryFluidStateBehaviour.BurnableQuery() {
            public boolean isBurnable(int x, int y, int z) {
                return x == 9 && y == 64 && z == 10;
            }
        }, 10, 64, 10));

        Assert.assertFalse(service.hasBurnableNeighbor(new StationaryFluidStateBehaviour.BurnableQuery() {
            public boolean isBurnable(int x, int y, int z) {
                return false;
            }
        }, 10, 64, 10));
    }
}
