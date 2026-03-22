package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.OutboundPacketDispatchSystem;
import net.minecraft.server.Packet0KeepAlive;
import org.junit.Assert;
import org.junit.Test;

public class OutboundPacketDispatchServiceTest {
    @Test
    public void lowPriorityCheckUsesLegacyPacketFlag() {
        OutboundPacketDispatchSystem service = OutboundPacketDispatchSystem.getInstance();
        Packet0KeepAlive packet = new Packet0KeepAlive();

        Assert.assertFalse(service.isLowPriorityPacket(packet));
        packet.k = true;
        Assert.assertTrue(service.isLowPriorityPacket(packet));
    }
}
