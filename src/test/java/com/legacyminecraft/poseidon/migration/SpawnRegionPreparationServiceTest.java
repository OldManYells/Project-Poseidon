package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.SpawnRegionPreparationSystem;
import org.junit.Assert;
import org.junit.Test;

public class SpawnRegionPreparationServiceTest {
    @Test
    public void normalizeProgressTimestampUsesEarlierClockValue() {
        SpawnRegionPreparationSystem service = SpawnRegionPreparationSystem.getInstance();

        Assert.assertEquals(100L, service.normalizeProgressTimestamp(100L, 200L));
        Assert.assertEquals(200L, service.normalizeProgressTimestamp(300L, 200L));
    }

    @Test
    public void progressReportThresholdUsesStrictGreaterThanInterval() {
        SpawnRegionPreparationSystem service = SpawnRegionPreparationSystem.getInstance();

        Assert.assertFalse(service.shouldReportProgress(1000L, 0L, 1000L));
        Assert.assertTrue(service.shouldReportProgress(1001L, 0L, 1000L));
    }

    @Test
    public void progressPercentMatchesLegacyFormula() {
        SpawnRegionPreparationSystem service = SpawnRegionPreparationSystem.getInstance();

        Assert.assertEquals(0, service.calculateProgressPercent(196, -196, -196));
        Assert.assertEquals(49, service.calculateProgressPercent(196, 0, 0));
        Assert.assertEquals(99, service.calculateProgressPercent(196, 196, 196));
    }
}
