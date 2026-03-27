package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyNetworkLifecycleWrappersThinnessTest {
    private static final Path NETWORK_LISTEN_THREAD_PATH =
            Paths.get("src/main/java/net/minecraft/server/NetworkListenThread.java");
    private static final Path NETWORK_ACCEPT_THREAD_PATH =
            Paths.get("src/main/java/net/minecraft/server/NetworkAcceptThread.java");
    private static final Path THREAD_MONITOR_CONNECTION_PATH =
            Paths.get("src/main/java/net/minecraft/server/ThreadMonitorConnection.java");

    @Test
    public void networkListenThreadUsesCanonicalSystemNaming() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_LISTEN_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("NetworkListenBootstrap"));
        Assert.assertTrue(text.contains("NetworkConnectionPumpSystem"));
        Assert.assertTrue(text.contains("networkListenBootstrap"));
        Assert.assertTrue(text.contains("networkConnectionPumpSystem"));
        Assert.assertFalse(text.contains("networkListenBootstrapService"));
        Assert.assertFalse(text.contains("networkConnectionPumpService"));
    }

    @Test
    public void networkAcceptThreadUsesFieldBackedAcceptLoopOperations() throws IOException {
        String text = new String(Files.readAllBytes(NETWORK_ACCEPT_THREAD_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConnectionAcceptLoopSystem"));
        Assert.assertTrue(text.contains("ConnectionAcceptExceptionPolicy"));
        Assert.assertTrue(text.contains("NetworkConnectionLabelPolicy"));
        Assert.assertTrue(text.contains("connectionAcceptLoopSystem"));
        Assert.assertTrue(text.contains("connectionAcceptExceptionPolicy"));
        Assert.assertTrue(text.contains("networkConnectionLabelPolicy"));
        Assert.assertTrue(text.contains("acceptLoopOperations"));
        Assert.assertTrue(text.contains("connectionAcceptLoopSystem.runLoop(this.acceptLoopOperations);"));
        Assert.assertFalse(text.contains("\"Connection #\""));
        Assert.assertFalse(text.contains("ioexception.printStackTrace()"));
        Assert.assertFalse(text.contains("connectionAcceptLoopService"));
    }

    @Test
    public void threadMonitorConnectionUsesFieldBackedMonitorDelegates() throws IOException {
        String text = new String(Files.readAllBytes(THREAD_MONITOR_CONNECTION_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConnectionMonitorSystem"));
        Assert.assertTrue(text.contains("NetworkDisconnectKeyPolicy"));
        Assert.assertTrue(text.contains("NetworkDisconnectArgumentPolicy"));
        Assert.assertTrue(text.contains("connectionMonitorSystem"));
        Assert.assertTrue(text.contains("networkDisconnectKeyPolicy"));
        Assert.assertTrue(text.contains("networkDisconnectArgumentPolicy"));
        Assert.assertTrue(text.contains("connectionState"));
        Assert.assertTrue(text.contains("interruptWriter"));
        Assert.assertTrue(text.contains("disconnectAction"));
        Assert.assertFalse(text.contains("\"disconnect.closed\""));
        Assert.assertFalse(text.contains("new Object[0]"));
        Assert.assertFalse(text.contains("connectionMonitorService"));
    }
}
