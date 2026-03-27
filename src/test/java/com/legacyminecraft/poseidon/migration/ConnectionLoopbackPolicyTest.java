package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionLoopbackPolicy;
import org.junit.Assert;
import org.junit.Test;

import java.net.InetAddress;

public class ConnectionLoopbackPolicyTest {
    @Test
    public void matchesLegacyLoopbackAddressRule() throws Exception {
        ConnectionLoopbackPolicy policy = ConnectionLoopbackPolicy.getInstance();

        Assert.assertTrue(policy.isLoopback(InetAddress.getByName("127.0.0.1")));
        Assert.assertFalse(policy.isLoopback(InetAddress.getByName("203.0.113.7")));
        Assert.assertFalse(policy.isLoopback(null));
    }
}
