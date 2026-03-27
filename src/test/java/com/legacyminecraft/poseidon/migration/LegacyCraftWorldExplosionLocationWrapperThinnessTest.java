package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldExplosionLocationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_EXPLOSION_LOCATION_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldExplosionLocationBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesExplosionLocationOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_EXPLOSION_LOCATION_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean createExplosion(Location loc, float power) {", "public Environment getEnvironment() {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldExplosionLocationBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EXPLOSION_LOCATION_BRIDGE_BEHAVIOUR.createExplosion(this, loc, power)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EXPLOSION_LOCATION_BRIDGE_BEHAVIOUR.createExplosion(this, loc, power, setFire)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EXPLOSION_LOCATION_BRIDGE_BEHAVIOUR.createExplosion("));

        Assert.assertFalse(section.contains("return createExplosion(loc, power, false);"));
        Assert.assertFalse(section.contains("return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire);"));
        Assert.assertFalse(section.contains("return createExplosion(loc.getX(), loc.getY(), loc.getZ(), power, setFire, customDamageCause);"));

        Assert.assertTrue(behaviourText.contains("createExplosion(CraftWorld craftWorld, Location location, float power)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.createExplosion(location, power, false);"));
        Assert.assertTrue(behaviourText.contains("createExplosion(CraftWorld craftWorld, Location location, float power, boolean setFire)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.createExplosion(location.getX(), location.getY(), location.getZ(), power, setFire);"));
        Assert.assertTrue(behaviourText.contains("createExplosion("));
        Assert.assertTrue(behaviourText.contains("location.getX()"));
        Assert.assertTrue(behaviourText.contains("location.getY()"));
        Assert.assertTrue(behaviourText.contains("location.getZ()"));
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
