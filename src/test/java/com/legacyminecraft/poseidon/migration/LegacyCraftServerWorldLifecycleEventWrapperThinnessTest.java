package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldLifecycleEventWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_LIFECYCLE_EVENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldLifecycleEventBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldLifecycleEventWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_LIFECYCLE_EVENT_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldLifecycleEventBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_LIFECYCLE_EVENT_BEHAVIOUR.dispatchWorldInit(pluginManager, internal);"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_LIFECYCLE_EVENT_BEHAVIOUR.dispatchWorldLoad(pluginManager, internal);"));

        Assert.assertFalse(section.contains("pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));"));
        Assert.assertFalse(section.contains("pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));"));

        Assert.assertTrue(behaviourText.contains("dispatchWorldInit(PluginManager pluginManager, WorldServer internal)"));
        Assert.assertTrue(behaviourText.contains("pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));"));
        Assert.assertTrue(behaviourText.contains("dispatchWorldLoad(PluginManager pluginManager, WorldServer internal)"));
        Assert.assertTrue(behaviourText.contains("pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));"));
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
