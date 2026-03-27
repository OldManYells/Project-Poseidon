package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketEventConfigPolicy;
import org.junit.Assert;
import org.junit.Test;

public class PacketEventConfigPolicyTest {
    @Test
    public void exposesPacketEventsConfigKeyAndDefault() {
        PacketEventConfigPolicy policy = PacketEventConfigPolicy.getInstance();

        Assert.assertEquals("settings.packet-events.enabled", policy.packetEventsEnabledKey());
        Assert.assertFalse(policy.packetEventsEnabledDefault());
    }
}
