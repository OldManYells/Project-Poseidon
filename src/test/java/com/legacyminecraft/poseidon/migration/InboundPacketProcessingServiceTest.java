package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.InboundPacketProcessingSystem;
import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;
import org.junit.Assert;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InboundPacketProcessingServiceTest {
    @Test
    public void processingBudgetUsesLegacyPostDecrementBehavior() {
        InboundPacketProcessingSystem service = InboundPacketProcessingSystem.getInstance();
        List inboundQueue = new ArrayList();
        DummyPacket first = new DummyPacket();
        DummyPacket second = new DummyPacket();
        DummyPacket third = new DummyPacket();
        inboundQueue.add(first);
        inboundQueue.add(second);
        inboundQueue.add(third);

        int processed = service.processInboundQueue(inboundQueue, 1, new DummyNetHandler(), false, "Alex");

        Assert.assertEquals(2, processed);
        Assert.assertEquals(1, inboundQueue.size());
        Assert.assertEquals(1, first.getDispatchCount());
        Assert.assertEquals(1, second.getDispatchCount());
        Assert.assertEquals(0, third.getDispatchCount());
    }

    @Test
    public void negativeBudgetProcessesNothing() {
        InboundPacketProcessingSystem service = InboundPacketProcessingSystem.getInstance();
        List inboundQueue = new ArrayList();
        DummyPacket packet = new DummyPacket();
        inboundQueue.add(packet);

        int processed = service.processInboundQueue(inboundQueue, -1, new DummyNetHandler(), false, "Alex");

        Assert.assertEquals(0, processed);
        Assert.assertEquals(1, inboundQueue.size());
        Assert.assertEquals(0, packet.getDispatchCount());
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
