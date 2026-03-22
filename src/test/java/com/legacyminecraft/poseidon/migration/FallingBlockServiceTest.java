package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FallingBlockBehaviour;
import net.minecraft.server.Material;
import org.junit.Assert;
import org.junit.Test;

public class FallingBlockServiceTest {
    @Test
    public void fallThroughAndSchedulingRulesMatchLegacyBehavior() {
        FallingBlockBehaviour service = FallingBlockBehaviour.getInstance();

        Assert.assertEquals(3, service.updateDelayTicks());
        Assert.assertEquals(32, service.chunkCheckRadius());
        Assert.assertTrue(service.shouldAttemptFall(true, 0));
        Assert.assertFalse(service.shouldAttemptFall(false, 10));
        Assert.assertFalse(service.shouldAttemptFall(true, -1));
        Assert.assertTrue(service.shouldSpawnFallingEntity(false, true));
        Assert.assertFalse(service.shouldSpawnFallingEntity(true, true));
        Assert.assertFalse(service.shouldSpawnFallingEntity(false, false));

        Assert.assertTrue(service.canFallThrough(0, 51, null));
        Assert.assertTrue(service.canFallThrough(51, 51, Material.STONE));
        Assert.assertTrue(service.canFallThrough(1, 51, Material.WATER));
        Assert.assertTrue(service.canFallThrough(1, 51, Material.LAVA));
        Assert.assertFalse(service.canFallThrough(1, 51, Material.STONE));
    }

    @Test
    public void settlingAndDupingFixRulesMatchLegacyBehavior() {
        FallingBlockBehaviour service = FallingBlockBehaviour.getInstance();

        int settledY = service.resolveSettledY(new FallingBlockBehaviour.FallThroughQuery() {
            public boolean canFallThrough(int x, int y, int z) {
                return y >= 2;
            }
        }, 0, 6, 0);
        Assert.assertEquals(2, settledY);
        Assert.assertTrue(service.canSettleAt(settledY));
        Assert.assertFalse(service.canSettleAt(0));
        Assert.assertTrue(service.shouldApplyDupingFix(true));
        Assert.assertFalse(service.shouldApplyDupingFix(false));
    }
}
