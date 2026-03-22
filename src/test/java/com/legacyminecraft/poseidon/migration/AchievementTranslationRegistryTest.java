package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.AchievementTranslationRegistry;
import org.junit.Assert;
import org.junit.Test;

public class AchievementTranslationRegistryTest {
    @Test
    public void loadsKnownStatisticTranslation() {
        String translation = AchievementTranslationRegistry.getInstance().lookup(1000);
        Assert.assertEquals("43ddd8b48469d9a8c011718aa846facb", translation);
    }

    @Test
    public void unknownStatisticTranslationReturnsNull() {
        Assert.assertNull(AchievementTranslationRegistry.getInstance().lookup(-12345));
    }
}
