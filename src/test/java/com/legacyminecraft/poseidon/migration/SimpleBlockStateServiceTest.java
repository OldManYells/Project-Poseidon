package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SimpleBlockStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class SimpleBlockStateServiceTest {
    @Test
    public void simpleTextureDropAndOpacityRulesMatchLegacyBehavior() {
        SimpleBlockStateBehaviour service = SimpleBlockStateBehaviour.getInstance();

        Assert.assertFalse(service.isOpaqueCubeFalse());
        Assert.assertEquals(4, service.resolveBookshelfTextureBySide(0, 47));
        Assert.assertEquals(4, service.resolveBookshelfTextureBySide(1, 47));
        Assert.assertEquals(47, service.resolveBookshelfTextureBySide(2, 47));
        Assert.assertEquals(0, service.resolveNoDropCount());
        Assert.assertEquals(1, service.resolveFixedDropCount(1));
        Assert.assertEquals(49, service.resolveFixedDropItemId(49));
        Assert.assertEquals(4, service.resolveStoneDropItemId(4));
        Assert.assertEquals(42, service.resolveTextureIdentity(42));
    }
}
