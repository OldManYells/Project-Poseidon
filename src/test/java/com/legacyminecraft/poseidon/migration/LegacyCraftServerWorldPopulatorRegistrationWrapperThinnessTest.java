package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldPopulatorRegistrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_POPULATOR_REGISTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldPopulatorRegistrationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldPopulatorRegistrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_POPULATOR_REGISTRATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldPopulatorRegistrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_POPULATOR_REGISTRATION_BEHAVIOUR.registerPopulators(internal, resolvedGenerator);"));

        Assert.assertFalse(section.contains("if (generator != null) {"));
        Assert.assertFalse(section.contains("internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));"));

        Assert.assertTrue(behaviourText.contains("registerPopulators(WorldServer internal, ChunkGenerator generator)"));
        Assert.assertTrue(behaviourText.contains("if (generator != null) {"));
        Assert.assertTrue(behaviourText.contains("internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));"));
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
