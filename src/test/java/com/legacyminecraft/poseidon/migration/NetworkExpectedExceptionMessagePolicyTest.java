package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkExpectedExceptionMessagePolicy;
import org.junit.Assert;
import org.junit.Test;

public class NetworkExpectedExceptionMessagePolicyTest {
    @Test
    public void identifiesExpectedDisconnectMessages() {
        NetworkExpectedExceptionMessagePolicy policy = NetworkExpectedExceptionMessagePolicy.getInstance();

        Assert.assertTrue(policy.isExpectedDisconnectMessage("Socket closed"));
        Assert.assertTrue(policy.isExpectedDisconnectMessage("Broken pipe"));
        Assert.assertTrue(policy.isExpectedDisconnectMessage("Read timed out"));
        Assert.assertFalse(policy.isExpectedDisconnectMessage("Completely unrelated failure"));
        Assert.assertFalse(policy.isExpectedDisconnectMessage(null));
    }
}
