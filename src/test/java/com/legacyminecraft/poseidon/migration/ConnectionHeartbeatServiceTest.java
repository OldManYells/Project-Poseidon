package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionHeartbeatSystem;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionHeartbeatServiceTest {
    @Test
    public void keepAliveThresholdUsesStrictGreaterThan() {
        ConnectionHeartbeatSystem service = ConnectionHeartbeatSystem.getInstance();

        Assert.assertFalse(service.shouldSendKeepAlive(20, 0, 20));
        Assert.assertTrue(service.shouldSendKeepAlive(21, 0, 20));
    }

    @Test
    public void heartbeatDecisionAlwaysResetsAndPollsNetwork() {
        ConnectionHeartbeatSystem service = ConnectionHeartbeatSystem.getInstance();
        ConnectionHeartbeatSystem.HeartbeatDecision decision = service.evaluate(5, 0, 20);

        Assert.assertTrue(decision.shouldResetMovementProcessingFlag());
        Assert.assertTrue(decision.shouldPollNetwork());
        Assert.assertFalse(decision.shouldSendKeepAlive());
    }
}
