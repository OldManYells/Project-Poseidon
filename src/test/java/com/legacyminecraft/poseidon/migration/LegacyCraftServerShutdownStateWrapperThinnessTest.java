package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerShutdownStateWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_SHUTDOWN_STATE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerShutdownStateBehaviour.java");
    private static final Path SERVER_SHUTDOWN_STATE_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/ServerShutdownStateBridgeBehaviour.java");

    @Test
    public void craftServerDelegatesShutdownStateWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_SHUTDOWN_STATE_BEHAVIOUR_PATH);
        String bridgeText = read(SERVER_SHUTDOWN_STATE_BRIDGE_BEHAVIOUR_PATH);
        String shutdownSection = section(craftServerText, "public void shutdown() {", "public int broadcast(String message, String permission) {");
        String stateSection = section(craftServerText, "public boolean isShuttingdown() {", "//    public GameMode getDefaultGameMode() {");

        Assert.assertTrue(craftServerText.contains("CraftServerShutdownStateBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SHUTDOWN_STATE_BEHAVIOUR.shutdown("));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SHUTDOWN_STATE_BEHAVIOUR.isShuttingdown(shuttingdown)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SHUTDOWN_STATE_BEHAVIOUR.setShuttingdown("));

        Assert.assertFalse(shutdownSection.contains("setShuttingdown(true);"));
        Assert.assertFalse(shutdownSection.contains("console.a();"));
        Assert.assertFalse(stateSection.contains("return shuttingdown;"));
        Assert.assertFalse(stateSection.contains("this.shuttingdown = shuttingdown;"));

        Assert.assertTrue(behaviourText.contains("shutdown(MinecraftServer console, ShutdownStateSink shutdownStateSink)"));
        Assert.assertTrue(behaviourText.contains("shutdownStateSink.setShuttingDown(true);"));
        Assert.assertTrue(behaviourText.contains("console.a();"));
        Assert.assertTrue(behaviourText.contains("isShuttingdown(boolean shuttingDown)"));
        Assert.assertTrue(behaviourText.contains("setShuttingdown(ShutdownStateSink shutdownStateSink, boolean shuttingDown)"));
        Assert.assertTrue(behaviourText.contains("shutdownStateSink.setShuttingDown(shuttingDown);"));
        Assert.assertTrue(bridgeText.contains("ServerWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(bridgeText.contains("SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(server)"));
        Assert.assertFalse(bridgeText.contains("if (!(server instanceof CraftServer))"));
        Assert.assertFalse(bridgeText.contains("return ((CraftServer) server).isShuttingdown();"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
