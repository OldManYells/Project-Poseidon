package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldRegistryWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldRegistryBehaviour.java");

    @Test
    public void craftServerDelegatesWorldRegistryWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR_PATH);
        String worldSection = section(craftServerText, "public List<World> getWorlds() {", "public Logger getLogger() {");

        Assert.assertTrue(craftServerText.contains("CraftServerWorldRegistryBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR.getWorlds(worlds)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR.getWorld(worlds, name)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR.getWorld(worlds, uid)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR.addWorld(worlds, worldCandidate)"));

        Assert.assertFalse(worldSection.contains("return new ArrayList<World>(worlds.values());"));
        Assert.assertFalse(worldSection.contains("return worlds.get(name.toLowerCase());"));
        Assert.assertFalse(worldSection.contains("for (World world : worlds.values())"));
        Assert.assertFalse(worldSection.contains("worlds.put(world.getName().toLowerCase(), world);"));

        Assert.assertTrue(behaviourText.contains("getWorlds(Map<String, World> worldsByName)"));
        Assert.assertTrue(behaviourText.contains("return new ArrayList<World>(worldsByName.values());"));
        Assert.assertTrue(behaviourText.contains("getWorld(Map<String, World> worldsByName, String name)"));
        Assert.assertTrue(behaviourText.contains("worldsByName.get(name.toLowerCase())"));
        Assert.assertTrue(behaviourText.contains("getWorld(Map<String, World> worldsByName, UUID worldUid)"));
        Assert.assertTrue(behaviourText.contains("for (World world : worldsByName.values())"));
        Assert.assertTrue(behaviourText.contains("addWorld(Map<String, World> worldsByName, World world)"));
        Assert.assertTrue(behaviourText.contains("worldsByName.put(world.getName().toLowerCase(), world);"));
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
