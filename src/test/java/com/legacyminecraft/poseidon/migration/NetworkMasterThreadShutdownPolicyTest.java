package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkMasterThreadShutdownPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkMasterThreadShutdownPolicyTest {
    @Test
    public void exposesShutdownWaitAndForceStopRules() {
        NetworkMasterThreadShutdownPolicy policy = NetworkMasterThreadShutdownPolicy.getInstance();

        Assert.assertEquals(5000L, policy.shutdownWaitMillis());
        Assert.assertFalse(policy.shouldForceStop(null));
        Assert.assertFalse(policy.shouldForceStop(new Thread()));
    }
}
