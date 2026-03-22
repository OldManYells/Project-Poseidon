package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.ConnectionTerminationSystem;
import net.minecraft.server.Packet255KickDisconnect;
import org.junit.Assert;
import org.junit.Test;

public class ConnectionTerminationServiceTest {
    @Test
    public void helperPredicatesMatchLegacyDisconnectExpectations() {
        ConnectionTerminationSystem service = ConnectionTerminationSystem.getInstance();

        Assert.assertTrue(service.shouldSkipDisconnect(true));
        Assert.assertFalse(service.shouldSkipDisconnect(false));

        Assert.assertTrue(service.shouldBroadcastLeaveMessage("Alex left"));
        Assert.assertFalse(service.shouldBroadcastLeaveMessage(null));
    }

    @Test
    public void kickPacketFactoryCarriesReason() {
        ConnectionTerminationSystem service = ConnectionTerminationSystem.getInstance();
        Packet255KickDisconnect packet = service.createKickPacket("Nope");

        Assert.assertEquals("Nope", packet.a);
    }

    @Test
    public void terminateShortCircuitsWhenAlreadyDisconnected() {
        ConnectionTerminationSystem service = ConnectionTerminationSystem.getInstance();

        Assert.assertTrue(service.terminate(true, null, null, null, null, null, null, null));
    }
}
