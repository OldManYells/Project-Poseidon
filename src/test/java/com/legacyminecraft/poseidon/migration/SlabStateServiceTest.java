package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SlabStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class SlabStateServiceTest {
    @Test
    public void textureRulesMatchLegacyVariantAndSideMappings() {
        SlabStateBehaviour service = SlabStateBehaviour.getInstance();

        Assert.assertEquals(6, service.resolveTextureBySideAndVariant(1, 0));
        Assert.assertEquals(5, service.resolveTextureBySideAndVariant(2, 0));
        Assert.assertEquals(208, service.resolveTextureBySideAndVariant(0, 1));
        Assert.assertEquals(176, service.resolveTextureBySideAndVariant(1, 1));
        Assert.assertEquals(192, service.resolveTextureBySideAndVariant(3, 1));
        Assert.assertEquals(4, service.resolveTextureBySideAndVariant(2, 2));
        Assert.assertEquals(16, service.resolveTextureBySideAndVariant(2, 3));
    }

    @Test
    public void mergeAndDropRulesMatchLegacySlabBehavior() {
        SlabStateBehaviour service = SlabStateBehaviour.getInstance();

        Assert.assertTrue(service.shouldMergeWithBelow(44, 2, 2, 44));
        Assert.assertFalse(service.shouldMergeWithBelow(44, 2, 1, 44));
        Assert.assertFalse(service.shouldMergeWithBelow(43, 2, 2, 44));
        Assert.assertEquals(44, service.resolveDroppedItemId(44));
        Assert.assertEquals(2, service.resolveDropCount(true));
        Assert.assertEquals(1, service.resolveDropCount(false));
    }
}
