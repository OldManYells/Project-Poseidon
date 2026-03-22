package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.SleepingMovementPacketHandler;
import org.junit.Assert;
import org.junit.Test;

public class SleepingMovementPacketServiceTest {
    @Test
    public void sleepingPredicateMatchesInputState() {
        SleepingMovementPacketHandler service = SleepingMovementPacketHandler.getInstance();

        Assert.assertTrue(service.shouldHandleSleepingMovement(true));
        Assert.assertFalse(service.shouldHandleSleepingMovement(false));
    }
}
