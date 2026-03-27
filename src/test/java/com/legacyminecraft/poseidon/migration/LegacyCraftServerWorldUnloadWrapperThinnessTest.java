package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldUnloadWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_UNLOAD_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldUnloadBehaviour.java");

    @Test
    public void craftServerDelegatesWorldUnloadWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_UNLOAD_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public boolean unloadWorld(String name, boolean save) {", "public MinecraftServer getServer() {");

        Assert.assertTrue(craftServerText.contains("CraftServerWorldUnloadBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_UNLOAD_BEHAVIOUR.unloadWorld(name, save, worlds, console, pluginManager)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_UNLOAD_BEHAVIOUR.unloadWorld(world, save, worlds, console, pluginManager)"));

        Assert.assertFalse(section.contains("WorldServer handle = ((CraftWorld) world).getHandle();"));
        Assert.assertFalse(section.contains("handle.dimension > 1"));
        Assert.assertFalse(section.contains("handle.players.size() > 0"));
        Assert.assertFalse(section.contains("new WorldUnloadEvent("));
        Assert.assertFalse(section.contains("handle.save(true, (IProgressUpdate) null);"));
        Assert.assertFalse(section.contains("new WorldSaveEvent("));
        Assert.assertFalse(section.contains("worlds.remove(world.getName().toLowerCase());"));
        Assert.assertFalse(section.contains("console.worlds.remove(console.worlds.indexOf(handle));"));

        Assert.assertTrue(behaviourText.contains("unloadWorld(String name, boolean save, Map<String, World> worlds, MinecraftServer console, PluginManager pluginManager)"));
        Assert.assertTrue(behaviourText.contains("return unloadWorld(this.getWorld(name, worlds), save, worlds, console, pluginManager);"));
        Assert.assertTrue(behaviourText.contains("unloadWorld(World world, boolean save, Map<String, World> worlds, MinecraftServer console, PluginManager pluginManager)"));
        Assert.assertTrue(behaviourText.contains("WorldHandleBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("WORLD_HANDLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(behaviourText.contains("WorldServer handle = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world);"));
        Assert.assertTrue(behaviourText.contains("WorldUnloadEvent worldUnloadEvent = new WorldUnloadEvent(handle.getWorld());"));
        Assert.assertTrue(behaviourText.contains("if (worldUnloadEvent.isCancelled()) {"));
        Assert.assertTrue(behaviourText.contains("handle.save(true, (IProgressUpdate) null);"));
        Assert.assertTrue(behaviourText.contains("WorldSaveEvent worldSaveEvent = new WorldSaveEvent(handle.getWorld());"));
        Assert.assertTrue(behaviourText.contains("pluginManager.callEvent(worldSaveEvent);"));
        Assert.assertTrue(behaviourText.contains("worlds.remove(world.getName().toLowerCase());"));
        Assert.assertTrue(behaviourText.contains("console.worlds.remove(console.worlds.indexOf(handle));"));
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
