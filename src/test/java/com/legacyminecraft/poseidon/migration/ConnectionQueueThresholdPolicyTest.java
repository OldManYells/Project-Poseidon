package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionQueueThresholdPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionQueueThresholdPolicyTest {
    @Test
    public void exposesLegacyQueueThresholdsAndBudgets() {
        ConnectionQueueThresholdPolicy policy = ConnectionQueueThresholdPolicy.getInstance();

        Assert.assertEquals(1048576, policy.overflowThreshold(false));
        Assert.assertEquals(2097152, policy.overflowThreshold(true));
        Assert.assertEquals(100, policy.processingBudget(false));
        Assert.assertEquals(1000, policy.processingBudget(true));
    }
}
