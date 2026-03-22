package com.legacyminecraft.poseidon.migration;

import net.minecraft.server.StatisticList;
import org.junit.Assert;
import org.junit.Test;

public class StatisticBootstrapWiringTest {
    @Test
    public void coreStatisticsRemainRegisteredAndAccessibleAfterBootstrapDelegation() {
        Assert.assertNotNull(StatisticList.f);
        Assert.assertNotNull(StatisticList.B);
        Assert.assertNotNull(StatisticList.C);

        Assert.assertEquals(1000, StatisticList.f.e);
        Assert.assertEquals(1004, StatisticList.j.e);
        Assert.assertEquals(2025, StatisticList.B.e);
        Assert.assertEquals(256, StatisticList.C.length);

        Assert.assertTrue(StatisticList.b.contains(StatisticList.f));
        Assert.assertTrue(StatisticList.b.contains(StatisticList.B));
        Assert.assertTrue(StatisticList.c.contains(StatisticList.k));
    }
}
