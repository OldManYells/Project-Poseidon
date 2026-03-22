package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketEventPolicy;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

public class PacketEventPolicyTest {
    @Test
    public void incomingAllowanceInvertsCancellation() {
        PacketEventPolicy policy = PacketEventPolicy.getInstance();

        Assert.assertTrue(policy.isIncomingAllowed(false));
        Assert.assertFalse(policy.isIncomingAllowed(true));
    }

    @Test
    public void outgoingDispatchPolicyMatchesLegacyRules() {
        PacketEventPolicy policy = PacketEventPolicy.getInstance();

        Assert.assertTrue(policy.shouldDropOutgoingPacket(null));
        Assert.assertFalse(policy.shouldDropOutgoingPacket(new Packet0KeepAlive()));

        Assert.assertTrue(policy.shouldBypassOutgoingEventDispatch(false));
        Assert.assertFalse(policy.shouldBypassOutgoingEventDispatch(true));
    }
}
