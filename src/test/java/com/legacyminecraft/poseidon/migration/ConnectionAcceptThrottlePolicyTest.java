package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionAcceptThrottlePolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionAcceptThrottlePolicyTest {
    @Test
    public void exposesLegacyAcceptThrottleWindow() {
        Assert.assertEquals(5000L, ConnectionAcceptThrottlePolicy.getInstance().defaultThrottleMillis());
    }
}
