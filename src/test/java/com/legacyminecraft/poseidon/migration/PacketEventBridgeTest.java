package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PacketEventBridge;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

public class PacketEventBridgeTest {
    private final PacketEventBridge bridge = PacketEventBridge.getInstance();

    @Test
    public void filterOutgoingReturnsNullForNullPacket() {
        Assert.assertNull(bridge.filterOutgoingPacket(false, null, null));
    }

    @Test
    public void filterOutgoingReturnsOriginalPacketWhenEventsDisabled() {
        Packet packet = new Packet0KeepAlive();
        Assert.assertSame(packet, bridge.filterOutgoingPacket(false, null, packet));
    }
}
