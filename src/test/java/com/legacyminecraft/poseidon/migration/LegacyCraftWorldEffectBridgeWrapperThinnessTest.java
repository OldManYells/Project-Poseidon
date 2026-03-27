package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldEffectBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_EFFECT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldEffectBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesPlayEffectOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_EFFECT_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public void playEffect(Player player, Effect effect, int data) {", "public void playEffect(Location location, Effect effect, int data, int radius) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldEffectBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EFFECT_BRIDGE_BEHAVIOUR.playEffect(this, player, effect, data)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_EFFECT_BRIDGE_BEHAVIOUR.playEffect(this, location, effect, data)"));
        Assert.assertFalse(section.contains("playEffect(player.getLocation(), effect, data, 0);"));
        Assert.assertFalse(section.contains("playEffect(location, effect, data, 64);"));

        Assert.assertTrue(behaviourText.contains("playEffect(CraftWorld craftWorld, Player player, Effect effect, int data)"));
        Assert.assertTrue(behaviourText.contains("craftWorld.playEffect(player.getLocation(), effect, data, 0);"));
        Assert.assertTrue(behaviourText.contains("playEffect(CraftWorld craftWorld, Location location, Effect effect, int data)"));
        Assert.assertTrue(behaviourText.contains("craftWorld.playEffect(location, effect, data, 64);"));
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
