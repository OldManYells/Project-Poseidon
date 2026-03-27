package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNetworkManagerWrapperThinnessTest {
    private static final Path NETWORK_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/NetworkManager.java");

    @Test
    public void networkManagerUsesCanonicalSystemsWithAlignedNaming() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkManagerTickSystem"));
        Assert.assertTrue(text.contains("NetworkManagerConsoleLogBehaviour"));
        Assert.assertTrue(text.contains("InboundQueueReadSystem"));
        Assert.assertTrue(text.contains("InboundQueueReadExecutionSystem"));
        Assert.assertTrue(text.contains("OutboundQueueSystem"));
        Assert.assertTrue(text.contains("OutboundQueueEnqueueExecutionSystem"));
        Assert.assertTrue(text.contains("OutboundQueueDrainExecutionSystem"));
        Assert.assertTrue(text.contains("OutboundQueueDelayPolicy"));
        Assert.assertTrue(text.contains("PacketEventConfigPolicy"));
        Assert.assertTrue(text.contains("PacketSpamDetectionConfigPolicy"));
        Assert.assertTrue(text.contains("NetworkTransportConfigPolicy"));
        Assert.assertTrue(text.contains("NetworkThreadInterruptSystem"));
        Assert.assertTrue(text.contains("NetworkExceptionDisconnectSystem"));
        Assert.assertTrue(text.contains("NetworkExceptionLogBehaviour"));
        Assert.assertTrue(text.contains("NetworkDisconnectKeyPolicy"));
        Assert.assertTrue(text.contains("NetworkDisconnectArgumentPolicy"));
        Assert.assertTrue(text.contains("NetworkDisconnectLifecycleSystem"));
        Assert.assertTrue(text.contains("NetworkCloseMonitorStartSystem"));
        Assert.assertTrue(text.contains("NetworkSocketSystem"));
        Assert.assertTrue(text.contains("NetworkSocketInitializationFailureBehaviour"));
        Assert.assertTrue(text.contains("NetworkTickFinalizationSystem"));
        Assert.assertTrue(text.contains("networkManagerTickSystem"));
        Assert.assertTrue(text.contains("networkManagerConsoleLogBehaviour"));
        Assert.assertTrue(text.contains("inboundQueueReadSystem"));
        Assert.assertTrue(text.contains("inboundQueueReadExecutionSystem"));
        Assert.assertTrue(text.contains("outboundQueueSystem"));
        Assert.assertTrue(text.contains("outboundQueueEnqueueExecutionSystem"));
        Assert.assertTrue(text.contains("outboundQueueDrainExecutionSystem"));
        Assert.assertTrue(text.contains("outboundQueueDelayPolicy"));
        Assert.assertTrue(text.contains("packetEventConfigPolicy"));
        Assert.assertTrue(text.contains("packetSpamDetectionConfigPolicy"));
        Assert.assertTrue(text.contains("networkTransportConfigPolicy"));
        Assert.assertTrue(text.contains("networkThreadInterruptSystem"));
        Assert.assertTrue(text.contains("networkExceptionDisconnectSystem"));
        Assert.assertTrue(text.contains("networkExceptionLogBehaviour"));
        Assert.assertTrue(text.contains("networkDisconnectKeyPolicy"));
        Assert.assertTrue(text.contains("networkDisconnectArgumentPolicy"));
        Assert.assertTrue(text.contains("networkDisconnectLifecycleSystem"));
        Assert.assertTrue(text.contains("networkCloseMonitorStartSystem"));
        Assert.assertTrue(text.contains("networkSocketSystem"));
        Assert.assertTrue(text.contains("networkSocketInitializationFailureBehaviour"));
        Assert.assertTrue(text.contains("networkTickFinalizationSystem"));
        Assert.assertTrue(text.contains("disconnectActions"));
        Assert.assertTrue(text.contains("tickActions"));
        Assert.assertTrue(text.contains("tickFinalizationActions"));
        Assert.assertTrue(text.contains("inboundReadActions"));
        Assert.assertTrue(text.contains("outboundDrainActions"));
        Assert.assertTrue(text.contains("closeMonitorActions"));
        Assert.assertTrue(text.contains("exceptionActions"));
        Assert.assertTrue(text.contains("this.disconnectActions"));
        Assert.assertTrue(text.contains("this.tickActions"));
        Assert.assertTrue(text.contains("outboundQueueEnqueueExecutionSystem.execute("));
        Assert.assertTrue(text.contains("networkThreadInterruptSystem.interrupt(this.s, this.r);"));
        Assert.assertTrue(text.contains("networkExceptionDisconnectSystem.execute(exception, this.exceptionActions);"));
        Assert.assertTrue(text.contains("networkCloseMonitorStartSystem.start(this.closeMonitorActions);"));
        Assert.assertTrue(text.contains("networkTickFinalizationSystem.finalizeTick("));
        Assert.assertFalse(text.contains("networkManagerTickService"));
        Assert.assertFalse(text.contains("inboundQueueReadService"));
        Assert.assertFalse(text.contains("inboundQueueReadSystem.readNext("));
        Assert.assertFalse(text.contains("outboundQueueService"));
        Assert.assertFalse(text.contains("outboundQueueSystem.enqueuePacket("));
        Assert.assertFalse(text.contains("outboundQueueSystem.drain("));
        Assert.assertFalse(text.contains("networkDisconnectLifecycleService"));
        Assert.assertFalse(text.contains("networkSocketService"));
        Assert.assertFalse(text.contains("if (this.t && this.m.isEmpty())"));
        Assert.assertFalse(text.contains("synchronized (this.g)"));
        Assert.assertFalse(text.contains("public void d() {\n        this.a();"));
        Assert.assertFalse(text.contains("this.r.interrupt();"));
        Assert.assertFalse(text.contains("this.a(\"disconnect.genericReason\", new Object[]{\"Internal exception: \" + exception.toString()});"));
        Assert.assertFalse(text.contains("\"disconnect.endOfStream\""));
        Assert.assertFalse(text.contains("\"disconnect.genericReason\""));
        Assert.assertFalse(text.contains("new Object[]{reason}"));
        Assert.assertFalse(text.contains("new Object[0]"));
        Assert.assertFalse(text.contains("lowPriorityQueueDelay = 50"));
        Assert.assertFalse(text.contains("\"settings.packet-events.enabled\""));
        Assert.assertFalse(text.contains("\"settings.packet-spam-detection.enabled\""));
        Assert.assertFalse(text.contains("\"settings.packet-spam-detection.threshold\""));
        Assert.assertFalse(text.contains("\"settings.enable-tpc-nodelay\""));
        Assert.assertFalse(text.contains("\"settings.faster-packets.enabled\""));
        Assert.assertFalse(text.contains("(new ThreadMonitorConnection(this)).start();"));
        Assert.assertFalse(text.contains("System.err.println("));
        Assert.assertFalse(text.contains("System.out.println(message)"));
        Assert.assertFalse(text.contains("exception.printStackTrace()"));
    }
}
