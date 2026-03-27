package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkLoopPausePolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkLoopPausePolicyTest {
    @Test
    public void exposesLegacyLoopPauseDurations() {
        NetworkLoopPausePolicy policy = NetworkLoopPausePolicy.getInstance();

        Assert.assertEquals(2L, policy.fastLoopPauseMillis());
        Assert.assertEquals(100L, policy.standardLoopPauseMillis());
    }
}
