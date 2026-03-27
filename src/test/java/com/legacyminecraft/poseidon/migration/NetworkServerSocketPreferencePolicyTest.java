package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkServerSocketPreferencePolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkServerSocketPreferencePolicyTest {
    @Test
    public void exposesLegacyServerSocketPreferenceWeights() {
        NetworkServerSocketPreferencePolicy policy = NetworkServerSocketPreferencePolicy.getInstance();

        Assert.assertEquals(0, policy.connectionTimeWeight());
        Assert.assertEquals(2, policy.latencyWeight());
        Assert.assertEquals(1, policy.bandwidthWeight());
    }
}
