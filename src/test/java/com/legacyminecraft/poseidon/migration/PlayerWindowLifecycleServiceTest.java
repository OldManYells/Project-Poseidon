package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.PlayerWindowLifecycleBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PlayerWindowLifecycleServiceTest {
    @Test
    public void nextWindowIdWrapsAfterHundred() {
        PlayerWindowLifecycleBehaviour service = PlayerWindowLifecycleBehaviour.getInstance();

        Assert.assertEquals(1, service.nextWindowId(0));
        Assert.assertEquals(100, service.nextWindowId(99));
        Assert.assertEquals(1, service.nextWindowId(100));
    }

    @Test
    public void carriedItemSyncRuleMatchesSuppressionFlag() {
        PlayerWindowLifecycleBehaviour service = PlayerWindowLifecycleBehaviour.getInstance();

        Assert.assertTrue(service.shouldSendCarriedItemUpdate(false));
        Assert.assertFalse(service.shouldSendCarriedItemUpdate(true));
    }
}
