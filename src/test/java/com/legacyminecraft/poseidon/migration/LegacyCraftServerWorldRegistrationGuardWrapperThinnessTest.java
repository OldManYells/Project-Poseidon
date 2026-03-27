package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldRegistrationGuardWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_REGISTRATION_GUARD_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldRegistrationGuardBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldRegistrationGuardWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_REGISTRATION_GUARD_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldRegistrationGuardBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRATION_GUARD_BEHAVIOUR.canCompleteCreateWorld(worlds, worldName)"));

        Assert.assertFalse(section.contains("if (!(worlds.containsKey(name.toLowerCase()))) {"));

        Assert.assertTrue(behaviourText.contains("canCompleteCreateWorld(Map<String, World> worlds, String name)"));
        Assert.assertTrue(behaviourText.contains("return worlds.containsKey(name.toLowerCase());"));
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
