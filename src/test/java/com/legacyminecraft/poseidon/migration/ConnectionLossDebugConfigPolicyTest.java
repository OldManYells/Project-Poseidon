package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionLossDebugConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionLossDebugConfigPolicyTest {
    @Test
    public void exposesConnectionLossDebugConfigKeyAndDefault() {
        ConnectionLossDebugConfigPolicy policy = ConnectionLossDebugConfigPolicy.getInstance();

        Assert.assertEquals("settings.remove-join-leave-debug", policy.removeJoinLeaveDebugKey());
        Assert.assertTrue(policy.removeJoinLeaveDebugDefault());
    }
}
