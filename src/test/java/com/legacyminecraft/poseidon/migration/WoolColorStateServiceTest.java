package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.WoolColorStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class WoolColorStateServiceTest {
    @Test
    public void textureAndColorInversionRulesMatchLegacyWoolBehavior() {
        WoolColorStateBehaviour service = WoolColorStateBehaviour.getInstance();

        Assert.assertEquals(64, service.resolveTextureByData(64, 0));
        Assert.assertEquals(113, service.resolveTextureByData(64, 15));
        Assert.assertEquals(225, service.resolveTextureByData(64, 8));
        Assert.assertEquals(3, service.resolveDroppedData(3));
        Assert.assertEquals(15, service.invertColorData(0));
        Assert.assertEquals(0, service.invertColorData(15));
        Assert.assertEquals(10, service.invertColorData(5));
    }
}
