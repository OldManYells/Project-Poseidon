package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveDeltaThresholdPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveDeltaThresholdPolicyTest {
    @Test
    public void exposesLegacyMoveEventDeltaThresholds() {
        PlayerMoveDeltaThresholdPolicy policy = PlayerMoveDeltaThresholdPolicy.getInstance();

        Assert.assertEquals(1f / 256, policy.positionDeltaThreshold(), 0.0D);
        Assert.assertEquals(10f, policy.angleDeltaThreshold(), 0.0F);
    }
}
