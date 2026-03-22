package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.inventory.PlayerContainerSyncBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class PlayerContainerSyncServiceTest {
    @Test
    public void slotUpdateRuleRespectsResultSlotsAndSuppressionFlag() {
        PlayerContainerSyncBehaviour service = PlayerContainerSyncBehaviour.getInstance();

        Assert.assertTrue(service.shouldSendSlotUpdate(false, false));
        Assert.assertFalse(service.shouldSendSlotUpdate(true, false));
        Assert.assertFalse(service.shouldSendSlotUpdate(false, true));
        Assert.assertFalse(service.shouldSendSlotUpdate(true, true));
    }
}
