package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyMinecraftServerWrapperThinnessTest {
    private static final Path MINECRAFT_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/MinecraftServer.java");

    @Test
    public void minecraftServerRunDelegatesCoreLoopToCanonicalSystem() throws IOException {
        String text = new String(Files.readAllBytes(MINECRAFT_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ServerRunLoopSystem"));
        Assert.assertTrue(text.contains("serverRunLoopSystem.executeLoop("));
        Assert.assertTrue(text.contains("worldBootstrapSystem.bootstrapWorlds("));
        Assert.assertTrue(text.contains("worldLookupSystem.getWorldServer("));
        Assert.assertTrue(text.contains("worldLookupSystem.getTracker("));
        Assert.assertTrue(text.contains("serverNetworkStartupApplySystem"));
        Assert.assertTrue(text.contains("startupStateSink"));
        Assert.assertTrue(text.contains("serverNetworkStartupApplySystem.applyStartupResult("));
        Assert.assertTrue(text.contains("ModLoaderSupportConfigPolicy"));
        Assert.assertTrue(text.contains("modLoaderSupportConfigPolicy"));
        Assert.assertTrue(text.contains("serverRunFailureRecoverySystem"));
        Assert.assertTrue(text.contains("runFailureActions"));
        Assert.assertTrue(text.contains("serverRunFailureRecoverySystem.executeOnInitFailure("));
        Assert.assertTrue(text.contains("serverRunFailureRecoverySystem.executeOnException("));
        Assert.assertTrue(text.contains("serverRunTerminationSystem"));
        Assert.assertTrue(text.contains("runTerminationActions"));
        Assert.assertTrue(text.contains("serverRunTerminationSystem.executeTermination("));
        Assert.assertTrue(text.contains("serverWorldBootstrapProgressSystem"));
        Assert.assertTrue(text.contains("bootstrapProgressStateSink"));
        Assert.assertTrue(text.contains("bootstrapCompletionActions"));
        Assert.assertTrue(text.contains("serverWorldBootstrapProgressSystem.applyProgress("));
        Assert.assertTrue(text.contains("serverWorldBootstrapProgressSystem.completeBootstrap("));
        Assert.assertTrue(text.contains("serverCommandQueueBehaviour"));
        Assert.assertTrue(text.contains("serverCommandQueueBehaviour.enqueue("));
        Assert.assertTrue(text.contains("serverConsoleMessageLogSystem"));
        Assert.assertTrue(text.contains("serverConsoleMessageLogSystem.logInfo("));
        Assert.assertTrue(text.contains("serverConsoleMessageLogSystem.logWarning("));
        Assert.assertTrue(text.contains("playerListTickRegistrationSystem"));
        Assert.assertTrue(text.contains("playerListTickRegistrationSystem.register("));
        Assert.assertFalse(text.contains("for (long j = 0L; this.isRunning; Thread.sleep(1L))"));
        Assert.assertFalse(text.contains("serverTickTimingPolicy.normalizeElapsedMillis(k - i, log)"));
        Assert.assertFalse(text.contains("this.propertyManager = startupResult.getPropertyManager();"));
        Assert.assertFalse(text.contains("this.onlineMode = startupResult.isOnlineMode();"));
        Assert.assertFalse(text.contains("this.i = s;"));
        Assert.assertFalse(text.contains("this.j = i;"));
        Assert.assertFalse(text.contains("this.s.add(new ServerCommand(s, icommandlistener));"));
        Assert.assertFalse(text.contains("this.r.add(iupdateplayerlistbox);"));
        Assert.assertFalse(text.contains("log.log(Level.SEVERE, \"Unexpected exception\", throwable);"));
        Assert.assertFalse(text.contains("worldBootstrapService"));
        Assert.assertFalse(text.contains("worldLookupService"));
        Assert.assertFalse(text.contains("\"settings.support.modloader.enable\""));
    }
}
