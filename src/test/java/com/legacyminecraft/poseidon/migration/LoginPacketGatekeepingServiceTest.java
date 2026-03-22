package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.auth.login.LoginPacketGatekeepingService;
import net.minecraft.server.Packet1Login;
import org.junit.Assert;
import org.junit.Test;

public class LoginPacketGatekeepingServiceTest {
    @Test
    public void duplicateLoginPacketsAreRejected() {
        LoginPacketGatekeepingService service = LoginPacketGatekeepingService.getInstance();
        Packet1Login packet = new Packet1Login("Steve", 14, 0L, (byte) 0);

        LoginPacketGatekeepingService.GatekeepingResult result = service.evaluate(packet, true);

        Assert.assertFalse(result.isAccepted());
        Assert.assertEquals("Multiple login packets received.", result.getDisconnectMessage());
    }

    @Test
    public void acceptedPacketCarriesUsernameAndProtocolKickMessageWhenNeeded() {
        LoginPacketGatekeepingService service = LoginPacketGatekeepingService.getInstance();

        LoginPacketGatekeepingService.GatekeepingResult valid =
                service.evaluate(new Packet1Login("Steve", 14, 0L, (byte) 0), false);
        Assert.assertTrue(valid.isAccepted());
        Assert.assertEquals("Steve", valid.getUsername());
        Assert.assertNull(valid.getProtocolKickMessage());

        LoginPacketGatekeepingService.GatekeepingResult outdatedServer =
                service.evaluate(new Packet1Login("Alex", 15, 0L, (byte) 0), false);
        Assert.assertTrue(outdatedServer.isAccepted());
        Assert.assertEquals("Alex", outdatedServer.getUsername());
        Assert.assertEquals("Outdated server! I'm still on Beta 1.7.3", outdatedServer.getProtocolKickMessage());
    }
}
