package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNetHandlerWrapperThinnessTest {
    private static final Path NET_HANDLER_PATH = Paths.get("src/main/java/net/minecraft/server/NetHandler.java");

    @Test
    public void netHandlerTypedPacketCallbacksDelegateToCanonicalForwardingBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(NET_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import com.legacyminecraft.poseidon.network.NetHandlerPacketForwardingBehaviour;"));
        Assert.assertTrue(text.contains("NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet0KeepAlive);"));
        Assert.assertTrue(text.contains("NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet255kickdisconnect);"));
        Assert.assertTrue(text.contains("NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet131);"));
        Assert.assertTrue(text.contains("NET_HANDLER_PACKET_FORWARDING_BEHAVIOUR.forwardToGeneric(this, packet61);"));
        Assert.assertFalse(text.contains("this.a((Packet) packet0KeepAlive);"));
        Assert.assertFalse(text.contains("this.a((Packet) packet131);"));
    }
}
