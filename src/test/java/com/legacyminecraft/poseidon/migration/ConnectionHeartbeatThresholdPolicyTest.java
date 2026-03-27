package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionHeartbeatThresholdPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionHeartbeatThresholdPolicyTest {
    @Test
    public void exposesLegacyKeepAliveThresholdTicks() {
        Assert.assertEquals(20, ConnectionHeartbeatThresholdPolicy.getInstance().keepAliveThresholdTicks());
    }
}
