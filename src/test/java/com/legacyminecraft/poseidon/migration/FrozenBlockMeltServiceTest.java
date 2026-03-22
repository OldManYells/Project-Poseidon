package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FrozenBlockMeltBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class FrozenBlockMeltServiceTest {
    @Test
    public void meltThresholdAndDropRulesMatchLegacyFrozenBlocks() {
        FrozenBlockMeltBehaviour service = FrozenBlockMeltBehaviour.getInstance();

        Assert.assertEquals(11, service.snowMeltThreshold());
        Assert.assertTrue(service.shouldMelt(12, service.snowMeltThreshold()));
        Assert.assertFalse(service.shouldMelt(11, service.snowMeltThreshold()));

        Assert.assertEquals(10, service.iceMeltThreshold(1));
        Assert.assertTrue(service.shouldMelt(11, service.iceMeltThreshold(1)));
        Assert.assertFalse(service.shouldMelt(10, service.iceMeltThreshold(1)));

        Assert.assertEquals(4, service.resolveSnowBlockDropCount());
    }

    @Test
    public void harvestedIceWaterConversionMatchesLegacyMaterialCheck() {
        FrozenBlockMeltBehaviour service = FrozenBlockMeltBehaviour.getInstance();

        Assert.assertTrue(service.shouldConvertHarvestedIceToWater(true, false));
        Assert.assertTrue(service.shouldConvertHarvestedIceToWater(false, true));
        Assert.assertTrue(service.shouldConvertHarvestedIceToWater(true, true));
        Assert.assertFalse(service.shouldConvertHarvestedIceToWater(false, false));
    }
}
