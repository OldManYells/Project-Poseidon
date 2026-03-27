package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerMapWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_MAP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerMapBehaviour.java");

    @Test
    public void craftServerDelegatesMapLookupAndCreationToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_MAP_BEHAVIOUR_PATH);
        String getMapSection = section(craftServerText,
                "public CraftMapView getMap(short id) {",
                "public CraftMapView createMap(World world) {");
        String createMapSection = section(craftServerText,
                "public CraftMapView createMap(World world) {",
                "public void shutdown() {");

        Assert.assertTrue(craftServerText.contains("CraftServerMapBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_MAP_BEHAVIOUR.getMap(console, id)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_MAP_BEHAVIOUR.createMap(world)"));

        Assert.assertFalse(getMapSection.contains("console.worlds.get(0).worldMaps"));
        Assert.assertFalse(getMapSection.contains("collection.a(WorldMap.class, \"map_\" + id)"));
        Assert.assertFalse(getMapSection.contains("return worldmap.mapView;"));
        Assert.assertFalse(getMapSection.contains("if (worldmap == null)"));

        Assert.assertFalse(createMapSection.contains("new ItemStack(Item.MAP, 1, -1)"));
        Assert.assertFalse(createMapSection.contains("Item.MAP.a(stack, ((CraftWorld) world).getHandle())"));
        Assert.assertFalse(createMapSection.contains("return worldmap.mapView;"));

        Assert.assertTrue(behaviourText.contains("CraftServerMapBehaviour"));
        Assert.assertTrue(behaviourText.contains("getMap(MinecraftServer console, short id)"));
        Assert.assertTrue(behaviourText.contains("console.worlds.get(0).worldMaps"));
        Assert.assertTrue(behaviourText.contains("collection.a(WorldMap.class, \"map_\" + id)"));
        Assert.assertTrue(behaviourText.contains("if (worldMap == null)"));
        Assert.assertTrue(behaviourText.contains("createMap(World world)"));
        Assert.assertTrue(behaviourText.contains("WorldHandleBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("WORLD_HANDLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(behaviourText.contains("new ItemStack(Item.MAP, 1, -1)"));
        Assert.assertTrue(behaviourText.contains("Item.MAP.a(stack, WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(world))"));
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
