package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementPacketSentinelPolicy;
import org.junit.Assert;
import org.junit.Test;

public class MovementPacketSentinelPolicyTest {
    @Test
    public void detectsLegacyMotionOnlySentinelPair() {
        MovementPacketSentinelPolicy policy = MovementPacketSentinelPolicy.getInstance();

        Assert.assertTrue(policy.isMotionOnlySentinel(-999.0D, -999.0D));
        Assert.assertFalse(policy.isMotionOnlySentinel(0.0D, -999.0D));
        Assert.assertFalse(policy.isMotionOnlySentinel(-999.0D, 0.0D));
    }
}
