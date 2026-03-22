package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutgoingPacketEventSystem;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

public class OutgoingPacketEventServiceTest {
    @Test
    public void nullPacketIsDropped() {
        OutgoingPacketEventSystem service = OutgoingPacketEventSystem.getInstance();

        Assert.assertNull(service.filterOutgoingPacket(false, null, null));
    }

    @Test
    public void outgoingPacketPassesThroughWhenEventsDisabled() {
        OutgoingPacketEventSystem service = OutgoingPacketEventSystem.getInstance();
        Packet packet = new Packet0KeepAlive();

        Assert.assertSame(packet, service.filterOutgoingPacket(false, null, packet));
    }
}
