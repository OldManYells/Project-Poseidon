package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldExplosionBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_EXPLOSION_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldExplosionBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesExplosionDoubleOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_EXPLOSION_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean createExplosion(double x, double y, double z, float power) {", "public boolean createExplosion(double x, double y, double z, float power, boolean setFire, EntityDamageEvent.DamageCause customDamageCause){");

        Assert.assertTrue(craftWorldText.contains("CraftWorldExplosionBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EXPLOSION_BRIDGE_BEHAVIOUR.createExplosion(this, x, y, z, power)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EXPLOSION_BRIDGE_BEHAVIOUR.createExplosion(this, x, y, z, power, setFire)"));
        Assert.assertFalse(section.contains("return createExplosion(x, y, z, power, false);"));
        Assert.assertFalse(section.contains("return createExplosion(x, y, z, power, setFire, EntityDamageEvent.DamageCause.PLUGIN_EXPLOSION);"));

        Assert.assertTrue(behaviourText.contains("createExplosion(CraftWorld craftWorld, double x, double y, double z, float power)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.createExplosion(x, y, z, power, false);"));
        Assert.assertTrue(behaviourText.contains("createExplosion(CraftWorld craftWorld, double x, double y, double z, float power, boolean setFire)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.createExplosion(x, y, z, power, setFire, EntityDamageEvent.DamageCause.PLUGIN_EXPLOSION);"));
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
