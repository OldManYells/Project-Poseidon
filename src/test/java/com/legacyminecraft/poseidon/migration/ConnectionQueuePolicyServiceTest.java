package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionQueuePolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionQueuePolicyServiceTest {
    @Test
    public void overflowThresholdRespectsFastMode() {
        ConnectionQueuePolicy service = ConnectionQueuePolicy.getInstance();

        Assert.assertFalse(service.isOverflow(1048576, false));
        Assert.assertTrue(service.isOverflow(1048577, false));
        Assert.assertFalse(service.isOverflow(2097152, true));
        Assert.assertTrue(service.isOverflow(2097153, true));
    }

    @Test
    public void timeoutDecisionMirrorsLegacyIdleTickBehavior() {
        ConnectionQueuePolicy service = ConnectionQueuePolicy.getInstance();

        ConnectionQueuePolicy.TimeoutDecision activeDecision = service.evaluateTimeout(false, 55, 1200);
        Assert.assertFalse(activeDecision.shouldDisconnect());
        Assert.assertEquals(0, activeDecision.getNextIdleTicks());

        ConnectionQueuePolicy.TimeoutDecision idleDecision = service.evaluateTimeout(true, 1200, 1200);
        Assert.assertTrue(idleDecision.shouldDisconnect());
        Assert.assertEquals(1201, idleDecision.getNextIdleTicks());
    }

    @Test
    public void processingBudgetMatchesConfiguredMode() {
        ConnectionQueuePolicy service = ConnectionQueuePolicy.getInstance();

        Assert.assertEquals(100, service.getProcessingBudget(false));
        Assert.assertEquals(1000, service.getProcessingBudget(true));
    }
}
