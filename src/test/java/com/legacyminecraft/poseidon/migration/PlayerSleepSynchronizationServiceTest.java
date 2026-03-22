package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerSleepSynchronizationSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerSleepSynchronizationServiceTest {
    @Test
    public void sleepStartRuleRequiresUnsetSleepFlag() {
        PlayerSleepSynchronizationSystem service = PlayerSleepSynchronizationSystem.getInstance();

        Assert.assertTrue(service.shouldSendSleepStart(false));
        Assert.assertFalse(service.shouldSendSleepStart(true));
    }

    @Test
    public void wakeAnimationRuleMatchesSleepingState() {
        PlayerSleepSynchronizationSystem service = PlayerSleepSynchronizationSystem.getInstance();

        Assert.assertTrue(service.shouldBroadcastWakeAnimation(true));
        Assert.assertFalse(service.shouldBroadcastWakeAnimation(false));
    }

    @Test
    public void positionSyncRuleRequiresConnectedHandler() {
        PlayerSleepSynchronizationSystem service = PlayerSleepSynchronizationSystem.getInstance();

        Assert.assertTrue(service.shouldSyncPosition(true));
        Assert.assertFalse(service.shouldSyncPosition(false));
    }
}
