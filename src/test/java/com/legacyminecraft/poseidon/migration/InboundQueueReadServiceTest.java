package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.InboundQueueReadSystem;
import net.minecraft.server.NetHandler;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.List;

public class InboundQueueReadServiceTest {
    @Test
    public void endOfStreamProducesDisconnectDecision() throws Exception {
        InboundQueueReadSystem service = InboundQueueReadSystem.getInstance();
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(new byte[0]));
        List inboundQueue = new ArrayList();
        int[] inboundCounters = new int[256];

        InboundQueueReadSystem.ReadDecision decision =
                service.readNext(input, new DummyNetHandler(true), inboundCounters, inboundQueue);

        Assert.assertFalse(decision.isPacketQueued());
        Assert.assertTrue(decision.isEndOfStream());
        Assert.assertEquals(0, inboundQueue.size());
        Assert.assertEquals(0, inboundCounters[0]);
    }

    @Test
    public void packetReadUpdatesQueueAndCounters() throws Exception {
        InboundQueueReadSystem service = InboundQueueReadSystem.getInstance();
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(new byte[]{0}));
        List inboundQueue = new ArrayList();
        int[] inboundCounters = new int[256];

        InboundQueueReadSystem.ReadDecision decision =
                service.readNext(input, new DummyNetHandler(true), inboundCounters, inboundQueue);

        Assert.assertTrue(decision.isPacketQueued());
        Assert.assertFalse(decision.isEndOfStream());
        Assert.assertEquals(1, inboundQueue.size());
        Assert.assertEquals(1, inboundCounters[0]);
    }

    private static final class DummyNetHandler extends NetHandler {
        private final boolean serverSide;

        private DummyNetHandler(boolean serverSide) {
            this.serverSide = serverSide;
        }

        @Override
        public boolean c() {
            return serverSide;
        }
    }
}
