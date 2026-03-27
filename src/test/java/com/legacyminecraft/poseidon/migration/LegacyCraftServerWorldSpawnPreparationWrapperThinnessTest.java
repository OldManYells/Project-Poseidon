package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldSpawnPreparationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_SPAWN_PREPARATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldSpawnPreparationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldSpawnPreparationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_SPAWN_PREPARATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldSpawnPreparationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_SPAWN_PREPARATION_BEHAVIOUR.prepareSpawn(internal, worldName);"));

        Assert.assertFalse(section.contains("if (internal.getWorld().getKeepSpawnInMemory())"));
        Assert.assertFalse(section.contains("short short1 = 196;"));
        Assert.assertFalse(section.contains("System.out.println(\"Preparing spawn area for \" + name"));
        Assert.assertFalse(section.contains("internal.chunkProviderServer.getChunkAt("));
        Assert.assertFalse(section.contains("while (internal.doLighting())"));

        Assert.assertTrue(behaviourText.contains("prepareSpawn(WorldServer worldServer, String name)"));
        Assert.assertTrue(behaviourText.contains("if (!worldServer.getWorld().getKeepSpawnInMemory()) {"));
        Assert.assertTrue(behaviourText.contains("short radius = 196;"));
        Assert.assertTrue(behaviourText.contains("System.out.println(\"Preparing spawn area for \" + name"));
        Assert.assertTrue(behaviourText.contains("worldServer.chunkProviderServer.getChunkAt("));
        Assert.assertTrue(behaviourText.contains("while (worldServer.doLighting()) {"));
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
