package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.LockedChestStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class LockedChestStateServiceTest {
    @Test
    public void texturePlacementAndExpiryRulesMatchLegacyLockedChestBehavior() {
        LockedChestStateBehaviour service = LockedChestStateBehaviour.getInstance();

        Assert.assertEquals(25, service.resolveTextureBySide(1, 26));
        Assert.assertEquals(25, service.resolveTextureBySide(0, 26));
        Assert.assertEquals(27, service.resolveTextureBySide(3, 26));
        Assert.assertEquals(26, service.resolveTextureBySide(2, 26));
        Assert.assertTrue(service.canPlaceAtAnyLocation());
        Assert.assertEquals(0, service.expiredBlockTypeId());
    }
}
