package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PacketProtocolDelegationTest {
    private static final Path PACKET_PROTOCOL_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/packet/PacketProtocol.java");

    @Test
    public void packetProtocolServiceDelegatesDefaultPacketCatalogBootstrap() throws IOException {
        String text = new String(Files.readAllBytes(PACKET_PROTOCOL_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PacketRegistrationBootstrap"));
        Assert.assertTrue(text.contains("packetRegistrationBootstrapService.registerDefaults(this);"));
        Assert.assertFalse(text.contains("Packet0KeepAlive.class"));
    }

    @Test
    public void packetProtocolServiceDelegatesTrafficCountersToCanonicalCounterService() throws IOException {
        String text = new String(Files.readAllBytes(PACKET_PROTOCOL_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PacketTrafficCounter"));
        Assert.assertTrue(text.contains("packetTrafficCounterService.recordPacketStat"));
        Assert.assertFalse(text.contains("packetCounterSampleCount"));
        Assert.assertFalse(text.contains("packetCounters"));
    }

    @Test
    public void packetProtocolServiceDelegatesStringCodecToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(PACKET_PROTOCOL_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PacketStringCodec"));
        Assert.assertTrue(text.contains("packetStringCodecService.writeString"));
        Assert.assertTrue(text.contains("packetStringCodecService.readString"));
        Assert.assertFalse(text.contains("writeChars("));
    }

    @Test
    public void packetProtocolServiceDelegatesPacketInstantiationToCanonicalFactory() throws IOException {
        String text = new String(Files.readAllBytes(PACKET_PROTOCOL_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("PacketFactory"));
        Assert.assertTrue(text.contains("packetFactoryService.createPacket"));
        Assert.assertFalse(text.contains("newInstance()"));
    }
}
