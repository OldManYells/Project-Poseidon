package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.NetworkManagerTickSystem;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;
import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetworkManagerTickServiceTest {
    @Test
    public void tickAppliesOverflowAndProcessesInboundQueue() {
        NetworkManagerTickSystem service = NetworkManagerTickSystem.getInstance();
        List inboundQueue = new ArrayList();
        DummyPacket packet = new DummyPacket();
        inboundQueue.add(packet);
        CapturingActions actions = new CapturingActions();

        NetworkManagerTickSystem.TickState tickState = service.tick(
                new NetworkManagerTickSystem.TickRequest(
                        false,
                        1048577,
                        false,
                        42,
                        false,
                        inboundQueue.size(),
                        1000,
                        "Alex",
                        true,
                        inboundQueue,
                        new DummyNetHandler(),
                        false
                ),
                actions
        );

        Assert.assertTrue(actions.disconnectKeys.contains("disconnect.overflow"));
        Assert.assertNull(actions.kickReason);
        Assert.assertEquals(0, tickState.getNextIdleTicks());
        Assert.assertEquals(1, packet.getDispatchCount());
        Assert.assertTrue(inboundQueue.isEmpty());
    }

    @Test
    public void tickDisconnectsWhenIdleTimeoutReached() {
        NetworkManagerTickSystem service = NetworkManagerTickSystem.getInstance();
        CapturingActions actions = new CapturingActions();

        NetworkManagerTickSystem.TickState tickState = service.tick(
                new NetworkManagerTickSystem.TickRequest(
                        false,
                        0,
                        true,
                        1200,
                        false,
                        0,
                        1000,
                        "Alex",
                        true,
                        new ArrayList(),
                        new DummyNetHandler(),
                        false
                ),
                actions
        );

        Assert.assertTrue(actions.disconnectKeys.contains("disconnect.timeout"));
        Assert.assertEquals(1201, tickState.getNextIdleTicks());
    }

    @Test
    public void tickSpamKickLogsAndKicksPlayerConnections() {
        NetworkManagerTickSystem service = NetworkManagerTickSystem.getInstance();
        CapturingActions actions = new CapturingActions();

        service.tick(
                new NetworkManagerTickSystem.TickRequest(
                        false,
                        0,
                        true,
                        0,
                        true,
                        10,
                        1,
                        "Alex",
                        true,
                        new ArrayList(),
                        new DummyNetHandler(),
                        false
                ),
                actions
        );

        Assert.assertNull(findDisconnectKey(actions.disconnectKeys, "disconnect.spam"));
        Assert.assertNotNull(actions.kickReason);
        Assert.assertTrue(actions.kickReason.contains("packet spamming"));
        Assert.assertTrue(((String) actions.logMessages.get(0)).contains("Player Alex"));
    }

    @Test
    public void tickSpamDisconnectsNonPlayerConnections() {
        NetworkManagerTickSystem service = NetworkManagerTickSystem.getInstance();
        CapturingActions actions = new CapturingActions();

        service.tick(
                new NetworkManagerTickSystem.TickRequest(
                        false,
                        0,
                        true,
                        0,
                        true,
                        10,
                        1,
                        "ProxyClient",
                        false,
                        new ArrayList(),
                        new DummyNetHandler(),
                        false
                ),
                actions
        );

        Assert.assertTrue(actions.disconnectKeys.contains("disconnect.spam"));
        Assert.assertNull(actions.kickReason);
        Assert.assertEquals(1, actions.logMessages.size());
    }

    private String findDisconnectKey(List disconnectKeys, String key) {
        for (int i = 0; i < disconnectKeys.size(); i++) {
            Object value = disconnectKeys.get(i);
            if (key.equals(value)) {
                return key;
            }
        }
        return null;
    }

    private static final class CapturingActions implements NetworkManagerTickSystem.TickActions {
        private final List disconnectKeys = new ArrayList();
        private final List logMessages = new ArrayList();
        private String kickReason;

        @Override
        public void disconnect(String key) {
            disconnectKeys.add(key);
        }

        @Override
        public void kickPlayer(String reason) {
            kickReason = reason;
        }

        @Override
        public void log(String message) {
            logMessages.add(message);
        }
    }

    private static final class DummyNetHandler extends NetHandler {
        @Override
        public boolean c() {
            return false;
        }
    }

    private static final class DummyPacket extends Packet {
        private int dispatchCount;

        @Override
        public void a(DataInputStream datainputstream) throws IOException {
        }

        @Override
        public void a(DataOutputStream dataoutputstream) throws IOException {
        }

        @Override
        public void a(NetHandler nethandler) {
            dispatchCount++;
        }

        @Override
        public int a() {
            return 0;
        }

        public int getDispatchCount() {
            return dispatchCount;
        }
    }
}
