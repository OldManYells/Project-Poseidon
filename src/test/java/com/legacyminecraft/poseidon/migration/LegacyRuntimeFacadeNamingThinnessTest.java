package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyRuntimeFacadeNamingThinnessTest {
    private static final Path MINECRAFT_SERVER_PATH = Paths.get("src/main/java/net/minecraft/server/MinecraftServer.java");
    private static final Path CRAFTBUKKIT_MAIN_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/Main.java");
    private static final Path STOP_COMMAND_PATH = Paths.get("src/main/java/org/bukkit/command/defaults/StopCommand.java");

    @Test
    public void runtimeWrappersUseRoleAlignedRuntimeFacades() throws IOException {
        String minecraftServerText = read(MINECRAFT_SERVER_PATH);
        String craftBukkitMainText = read(CRAFTBUKKIT_MAIN_PATH);
        String stopCommandText = read(STOP_COMMAND_PATH);

        Assert.assertTrue(minecraftServerText.contains("ServerBootstrapPolicy"));
        Assert.assertTrue(minecraftServerText.contains("ServerCommandDrainLoopSystem"));
        Assert.assertTrue(minecraftServerText.contains("ModLoaderBootstrapSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerNetworkStartupSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerStartupFeedbackSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerChunkSaveCoordinatorSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerStopLifecycleSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerMainTickSystem"));
        Assert.assertTrue(minecraftServerText.contains("ServerEntryPointSystem"));
        Assert.assertFalse(minecraftServerText.contains("ServerBootstrapPolicyService"));
        Assert.assertFalse(minecraftServerText.contains("ServerCommandDrainLoopService"));
        Assert.assertFalse(minecraftServerText.contains("ModLoaderBootstrapService"));
        Assert.assertFalse(minecraftServerText.contains("ServerNetworkStartupService.StartupResult startupResult"));
        Assert.assertFalse(minecraftServerText.contains("ServerStartupFeedbackService"));
        Assert.assertFalse(minecraftServerText.contains("ServerChunkSaveCoordinatorService"));
        Assert.assertFalse(minecraftServerText.contains("ServerStopLifecycleService"));
        Assert.assertFalse(minecraftServerText.contains("ServerMainTickService"));
        Assert.assertFalse(minecraftServerText.contains("ServerEntryPointService"));

        Assert.assertTrue(craftBukkitMainText.contains("CraftBukkitBootstrapSystem"));
        Assert.assertFalse(craftBukkitMainText.contains("CraftBukkitBootstrapService"));

        Assert.assertTrue(stopCommandText.contains("StopCommandExecutionSystem"));
        Assert.assertFalse(stopCommandText.contains("StopCommandExecutionService"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

