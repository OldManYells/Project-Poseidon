package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveInitializationPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveInitializationPolicyTest {
    @Test
    public void exposesLegacyUninitializedMoveStateSentinels() {
        PlayerMoveInitializationPolicy policy = PlayerMoveInitializationPolicy.getInstance();

        Assert.assertEquals(Double.MAX_VALUE, policy.uninitializedCoordinate(), 0.0D);
        Assert.assertEquals(Float.MAX_VALUE, policy.uninitializedRotation(), 0.0F);
        Assert.assertFalse(policy.isInitializedCoordinate(Double.MAX_VALUE));
        Assert.assertTrue(policy.isInitializedCoordinate(0.0D));
    }
}
