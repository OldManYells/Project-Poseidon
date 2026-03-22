package com.legacyminecraft.poseidon.migration;

import net.minecraft.server.AchievementList;
import org.junit.Assert;
import org.junit.Test;

public class AchievementBootstrapWiringTest {
    @Test
    public void defaultAchievementGraphRemainsWiredAfterBootstrapDelegation() {
        Assert.assertNotNull(AchievementList.f);
        Assert.assertNotNull(AchievementList.u);
        Assert.assertEquals(16, AchievementList.e.size());

        Assert.assertSame(AchievementList.f, AchievementList.g.c);
        Assert.assertSame(AchievementList.g, AchievementList.h.c);
        Assert.assertSame(AchievementList.h, AchievementList.i.c);
        Assert.assertSame(AchievementList.k, AchievementList.q.c);
        Assert.assertSame(AchievementList.t, AchievementList.u.c);
    }
}
