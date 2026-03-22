package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.IncomingPlayerPacketDispatchSystem;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;
import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IncomingPlayerPacketDispatchServiceTest {
    @Test
    public void bypassesEventDispatchWhenFeatureDisabled() {
        IncomingPlayerPacketDispatchSystem service = IncomingPlayerPacketDispatchSystem.getInstance();
        Packet packet = new DummyPacket();

        IncomingPlayerPacketDispatchSystem.DispatchDecision decision =
                service.resolve(false, new DummyNetHandler(), "Alex", packet);

        Assert.assertTrue(decision.shouldDispatch());
        Assert.assertSame(packet, decision.getPacket());
    }

    @Test
    public void bypassesEventDispatchWhenHandlerIsNotNetServerHandler() {
        IncomingPlayerPacketDispatchSystem service = IncomingPlayerPacketDispatchSystem.getInstance();
        Packet packet = new DummyPacket();

        IncomingPlayerPacketDispatchSystem.DispatchDecision decision =
                service.resolve(true, new DummyNetHandler(), "Alex", packet);

        Assert.assertTrue(decision.shouldDispatch());
        Assert.assertSame(packet, decision.getPacket());
    }

    private static final class DummyNetHandler extends NetHandler {
        @Override
        public boolean c() {
            return false;
        }
    }

    private static final class DummyPacket extends Packet {
        @Override
        public void a(DataInputStream datainputstream) throws IOException {
        }

        @Override
        public void a(DataOutputStream dataoutputstream) throws IOException {
        }

        @Override
        public void a(NetHandler nethandler) {
        }

        @Override
        public int a() {
            return 0;
        }
    }
}
