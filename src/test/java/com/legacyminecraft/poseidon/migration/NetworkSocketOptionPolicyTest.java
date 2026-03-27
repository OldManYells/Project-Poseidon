package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkSocketOptionPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkSocketOptionPolicyTest {
    @Test
    public void exposesLegacySocketOptions() {
        NetworkSocketOptionPolicy policy = NetworkSocketOptionPolicy.getInstance();

        Assert.assertEquals(24, policy.trafficClass());
        Assert.assertEquals(30000, policy.socketTimeoutMillis());
        Assert.assertEquals(5120, policy.outputBufferBytes());
    }
}
