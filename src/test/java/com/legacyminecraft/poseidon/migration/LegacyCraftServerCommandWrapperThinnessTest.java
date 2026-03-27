package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerCommandWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_BROADCAST_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerBroadcastBehaviour.java");
    private static final Path CRAFT_SERVER_COMMAND_DISPATCH_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerCommandDispatchBehaviour.java");

    @Test
    public void craftServerDelegatesCommandAndBroadcastWrapperGlueToCanonicalBehaviours() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String broadcastBehaviourText = read(CRAFT_SERVER_BROADCAST_BEHAVIOUR_PATH);
        String commandDispatchBehaviourText = read(CRAFT_SERVER_COMMAND_DISPATCH_BEHAVIOUR_PATH);

        String broadcastMessageSection = section(craftServerText,
                "public int broadcastMessage(String message) {",
                "public Player getPlayer(final EntityPlayer entity) {");
        String serverCommandDispatchSection = section(craftServerText,
                "// NOTE: Should only be called from MinecraftServer.b()",
                "public void reload() {");

        Assert.assertTrue(craftServerText.contains("CraftServerBroadcastBehaviour"));
        Assert.assertTrue(craftServerText.contains("CraftServerCommandDispatchBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BROADCAST_BEHAVIOUR.broadcastMessage(getOnlinePlayers(), message)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_COMMAND_DISPATCH_BEHAVIOUR.dispatchCommand(commandMap, sender, serverCommand)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_COMMAND_DISPATCH_BEHAVIOUR.dispatchCommand(commandMap, sender, commandLine)"));
        Assert.assertFalse(broadcastMessageSection.contains("return broadcast(message, BROADCAST_CHANNEL_USERS);"));
        Assert.assertFalse(broadcastMessageSection.contains("for (Player player : players)"));
        Assert.assertFalse(serverCommandDispatchSection.contains("commandMap.dispatch(sender, commandLine)"));
        Assert.assertFalse(serverCommandDispatchSection.contains("Unknown command. Type \"help\" for help."));
        Assert.assertFalse(serverCommandDispatchSection.contains("return true;"));

        Assert.assertTrue(broadcastBehaviourText.contains("broadcastMessage(Player[] onlinePlayers, String message)"));
        Assert.assertTrue(broadcastBehaviourText.contains("for (Player player : onlinePlayers)"));
        Assert.assertTrue(broadcastBehaviourText.contains("player.sendMessage(message);"));
        Assert.assertTrue(broadcastBehaviourText.contains("return onlinePlayers.length;"));

        Assert.assertTrue(commandDispatchBehaviourText.contains("dispatchCommand(SimpleCommandMap commandMap, CommandSender sender, ServerCommand serverCommand)"));
        Assert.assertTrue(commandDispatchBehaviourText.contains("dispatchCommand(SimpleCommandMap commandMap, CommandSender sender, String commandLine)"));
        Assert.assertTrue(commandDispatchBehaviourText.contains("if (commandMap.dispatch(sender, commandLine))"));
        Assert.assertTrue(commandDispatchBehaviourText.contains("sender.sendMessage(\"Unknown command. Type \\\"help\\\" for help.\");"));
        Assert.assertTrue(commandDispatchBehaviourText.contains("return false;"));
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
