package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutboundQueueDelayPolicy;
import org.junit.Assert;
import org.junit.Test;

public class OutboundQueueDelayPolicyTest {
    @Test
    public void exposesLegacyInitialLowPriorityQueueDelay() {
        Assert.assertEquals(50, OutboundQueueDelayPolicy.getInstance().initialLowPriorityQueueDelay());
    }
}
