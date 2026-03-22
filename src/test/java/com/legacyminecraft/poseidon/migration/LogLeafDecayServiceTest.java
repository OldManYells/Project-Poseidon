package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.LogLeafDecayBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class LogLeafDecayServiceTest {
    @Test
    public void textureAndDecayBitRulesMatchLegacyBehavior() {
        LogLeafDecayBehaviour service = LogLeafDecayBehaviour.getInstance();

        Assert.assertEquals(4, service.decayRadius());
        Assert.assertEquals(5, service.decayRange(4));
        Assert.assertEquals(21, service.resolveTextureBySideAndVariant(1, 0));
        Assert.assertEquals(21, service.resolveTextureBySideAndVariant(0, 0));
        Assert.assertEquals(116, service.resolveTextureBySideAndVariant(2, 1));
        Assert.assertEquals(117, service.resolveTextureBySideAndVariant(2, 2));
        Assert.assertEquals(20, service.resolveTextureBySideAndVariant(2, 0));

        Assert.assertTrue(service.shouldMarkLeafForDecay(18, 18, 0));
        Assert.assertFalse(service.shouldMarkLeafForDecay(17, 18, 0));
        Assert.assertFalse(service.shouldMarkLeafForDecay(18, 18, 8));
        Assert.assertEquals(8, service.markLeafDataForDecay(0));
        Assert.assertEquals(15, service.markLeafDataForDecay(7));
    }
}
