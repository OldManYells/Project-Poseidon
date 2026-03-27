package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkTransportConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkTransportConfigPolicyTest {
    @Test
    public void exposesTransportConfigKeysAndDefaults() {
        NetworkTransportConfigPolicy policy = NetworkTransportConfigPolicy.getInstance();

        Assert.assertEquals("settings.enable-tpc-nodelay", policy.tcpNoDelayKey());
        Assert.assertFalse(policy.tcpNoDelayDefault());
        Assert.assertEquals("settings.faster-packets.enabled", policy.fasterPacketsEnabledKey());
        Assert.assertTrue(policy.fasterPacketsEnabledDefault());
    }
}
