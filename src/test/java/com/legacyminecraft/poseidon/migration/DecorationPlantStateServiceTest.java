package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.DecorationPlantStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class DecorationPlantStateServiceTest {
    @Test
    public void deadBushAndLongGrassRulesMatchLegacyBehavior() {
        DecorationPlantStateBehaviour service = DecorationPlantStateBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertEquals(-1, service.noDropItemId());
        Assert.assertTrue(service.deadBushCanPlaceOn(12, 12));
        Assert.assertFalse(service.deadBushCanPlaceOn(2, 12));
        Assert.assertEquals(31, service.deadBushTexture(31));

        Assert.assertEquals(31, service.longGrassTextureByData(1, 31));
        Assert.assertEquals(48, service.longGrassTextureByData(2, 31));
        Assert.assertEquals(47, service.longGrassTextureByData(0, 31));
        Assert.assertEquals(31, service.longGrassTextureByData(3, 31));
        Assert.assertEquals(295, service.longGrassDropItemId(deterministic, 295));
    }
}
