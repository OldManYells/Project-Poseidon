package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.LoginHandshakePacketHandler;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class LoginHandshakePacketServiceTest {
    @Test
    public void onlineModeGeneratesNewServerIdAndToken() {
        LoginHandshakePacketHandler service = LoginHandshakePacketHandler.getInstance();

        LoginHandshakePacketHandler.HandshakeDecision decision =
                service.createHandshakeDecision(true, "", new Random(12345L));

        Assert.assertEquals("5c9f20d58361b331", decision.getServerId());
        Assert.assertEquals("5c9f20d58361b331", decision.getResponsePacket().a);
    }

    @Test
    public void offlineModeKeepsServerIdAndUsesDashToken() {
        LoginHandshakePacketHandler service = LoginHandshakePacketHandler.getInstance();

        LoginHandshakePacketHandler.HandshakeDecision decision =
                service.createHandshakeDecision(false, "existing", new Random(999L));

        Assert.assertEquals("existing", decision.getServerId());
        Assert.assertEquals("-", decision.getResponsePacket().a);
    }
}
