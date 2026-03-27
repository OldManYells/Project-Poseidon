package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkTimeoutPolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkTimeoutPolicyTest {
    @Test
    public void exposesLegacyIdleTimeoutTicks() {
        Assert.assertEquals(1200, NetworkTimeoutPolicy.getInstance().idleTimeoutTicks());
    }
}
