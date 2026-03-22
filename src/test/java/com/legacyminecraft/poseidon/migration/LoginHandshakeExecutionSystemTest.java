package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.LoginHandshakeExecutionSystem;
import com.legacyminecraft.poseidon.network.LoginHandshakePacketHandler;
import net.minecraft.server.Packet;
import net.minecraft.server.Packet2Handshake;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class LoginHandshakeExecutionSystemTest {
    private final LoginHandshakeExecutionSystem loginHandshakeExecutionSystem = LoginHandshakeExecutionSystem.getInstance();
    private final LoginHandshakePacketHandler loginHandshakePacketHandler = LoginHandshakePacketHandler.getInstance();

    @Test
    public void executeHandshakeOfflineKeepsServerIdAndQueuesDashToken() {
        HandshakeActionCapture handshakeActionCapture = new HandshakeActionCapture();

        String serverId = loginHandshakeExecutionSystem.executeHandshake(
                false,
                "existing-id",
                new Random(1L),
                loginHandshakePacketHandler,
                handshakeActionCapture
        );

        Assert.assertEquals("existing-id", serverId);
        Assert.assertTrue(handshakeActionCapture.responsePacket instanceof Packet2Handshake);
        Assert.assertEquals("-", ((Packet2Handshake) handshakeActionCapture.responsePacket).a);
    }

    @Test
    public void executeHandshakeOnlineGeneratesServerIdAndQueuesToken() {
        HandshakeActionCapture handshakeActionCapture = new HandshakeActionCapture();

        String serverId = loginHandshakeExecutionSystem.executeHandshake(
                true,
                "",
                new Random(2L),
                loginHandshakePacketHandler,
                handshakeActionCapture
        );

        Assert.assertNotNull(serverId);
        Assert.assertFalse(serverId.isEmpty());
        Assert.assertTrue(handshakeActionCapture.responsePacket instanceof Packet2Handshake);
        Assert.assertEquals(serverId, ((Packet2Handshake) handshakeActionCapture.responsePacket).a);
    }

    private static final class HandshakeActionCapture implements LoginHandshakeExecutionSystem.HandshakeActions {
        private Packet responsePacket;

        @Override
        public void queueResponsePacket(Packet responsePacket) {
            this.responsePacket = responsePacket;
        }
    }
}
