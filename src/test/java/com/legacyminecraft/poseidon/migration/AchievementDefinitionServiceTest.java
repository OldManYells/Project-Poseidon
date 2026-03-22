package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.AchievementDefinitionBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class AchievementDefinitionServiceTest {
    private final AchievementDefinitionBehaviour service = AchievementDefinitionBehaviour.getInstance();

    @Test
    public void resolvesLegacyAchievementStatIdOffset() {
        Assert.assertEquals(5242895, service.toStatisticId(15));
    }

    @Test
    public void resolvesAchievementTitleAndDescriptionFromCanonicalTranslations() {
        Assert.assertEquals("Taking Inventory", service.resolveTitle("openInventory"));
        Assert.assertTrue(service.resolveDescription("openInventory").startsWith("Press"));
    }
}
