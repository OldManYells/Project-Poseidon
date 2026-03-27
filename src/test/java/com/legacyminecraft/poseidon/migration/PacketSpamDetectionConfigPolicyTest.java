package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketSpamDetectionConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PacketSpamDetectionConfigPolicyTest {
    @Test
    public void exposesSpamDetectionConfigKeysAndDefaults() {
        PacketSpamDetectionConfigPolicy policy = PacketSpamDetectionConfigPolicy.getInstance();

        Assert.assertEquals("settings.packet-spam-detection.enabled", policy.spamDetectionEnabledKey());
        Assert.assertTrue(policy.spamDetectionEnabledDefault());
        Assert.assertEquals("settings.packet-spam-detection.threshold", policy.spamDetectionThresholdKey());
        Assert.assertEquals(1000, policy.spamDetectionThresholdDefault());
    }
}
