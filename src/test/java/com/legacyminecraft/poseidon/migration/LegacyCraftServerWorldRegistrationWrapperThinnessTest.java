package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldRegistrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_REGISTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldRegistrationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldRegistrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_REGISTRATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldRegistrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRATION_BEHAVIOUR.registerWorld(console, internal);"));

        Assert.assertFalse(section.contains("internal.worldMaps = console.worlds.get(0).worldMaps;"));
        Assert.assertFalse(section.contains("internal.tracker = new EntityTracker(console, internal.dimension);"));
        Assert.assertFalse(section.contains("internal.addIWorldAccess((IWorldAccess) new WorldManager(console, internal));"));
        Assert.assertFalse(section.contains("internal.setSpawnFlags(true, true);"));
        Assert.assertFalse(section.contains("console.worlds.add(internal);"));

        Assert.assertTrue(behaviourText.contains("registerWorld(MinecraftServer console, WorldServer internal)"));
        Assert.assertTrue(behaviourText.contains("internal.worldMaps = console.worlds.get(0).worldMaps;"));
        Assert.assertTrue(behaviourText.contains("internal.tracker = new EntityTracker(console, internal.dimension);"));
        Assert.assertTrue(behaviourText.contains("internal.addIWorldAccess((IWorldAccess) new WorldManager(console, internal));"));
        Assert.assertTrue(behaviourText.contains("internal.setSpawnFlags(true, true);"));
        Assert.assertTrue(behaviourText.contains("console.worlds.add(internal);"));
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
