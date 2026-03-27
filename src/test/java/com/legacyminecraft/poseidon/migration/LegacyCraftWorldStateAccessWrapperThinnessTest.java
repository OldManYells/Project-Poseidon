package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldStateAccessWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path STATE_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldStateAccessBehaviour.java");

    @Test
    public void craftWorldDelegatesWeatherAndWorldStateAccessorsToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(STATE_ACCESS_BEHAVIOUR_PATH);

        String weatherSection = section(craftWorldText, "public boolean hasStorm() {", "public long getSeed() {");
        String stateSection = section(craftWorldText, "public boolean getPVP() {", "public void playEffect(Player player, Effect effect, int data) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldStateAccessBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.hasStorm(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getWeatherDuration(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.setWeatherDuration(world, duration)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.isThundering(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getThunderDuration(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.setThunderDuration(world, duration)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getPVP(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.setPVP(world, pvp)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.setSpawnFlags(world, allowMonsters, allowAnimals)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getAllowAnimals(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getAllowMonsters(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getMaxHeight(world)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_STATE_ACCESS_BEHAVIOUR.getKeepSpawnInMemory(world)"));

        Assert.assertFalse(weatherSection.contains("world.worldData.hasStorm()"));
        Assert.assertFalse(weatherSection.contains("world.worldData.getWeatherDuration()"));
        Assert.assertFalse(weatherSection.contains("world.worldData.setWeatherDuration(duration)"));
        Assert.assertFalse(weatherSection.contains("world.worldData.isThundering()"));
        Assert.assertFalse(weatherSection.contains("world.worldData.getThunderDuration()"));
        Assert.assertFalse(weatherSection.contains("world.worldData.setThunderDuration(duration)"));

        Assert.assertFalse(stateSection.contains("world.pvpMode"));
        Assert.assertFalse(stateSection.contains("world.allowAnimals"));
        Assert.assertFalse(stateSection.contains("world.allowMonsters"));
        Assert.assertFalse(stateSection.contains("world.keepSpawnInMemory"));
        Assert.assertFalse(stateSection.contains("world.setSpawnFlags(allowMonsters, allowAnimals)"));
        Assert.assertFalse(stateSection.contains("return 128;"));

        Assert.assertTrue(behaviourText.contains("public boolean hasStorm(WorldServer worldServer) {"));
        Assert.assertTrue(behaviourText.contains("return worldServer.worldData.hasStorm();"));
        Assert.assertTrue(behaviourText.contains("return worldServer.worldData.getWeatherDuration();"));
        Assert.assertTrue(behaviourText.contains("worldServer.worldData.setWeatherDuration(duration);"));
        Assert.assertTrue(behaviourText.contains("return worldServer.worldData.isThundering();"));
        Assert.assertTrue(behaviourText.contains("return worldServer.worldData.getThunderDuration();"));
        Assert.assertTrue(behaviourText.contains("worldServer.worldData.setThunderDuration(duration);"));
        Assert.assertTrue(behaviourText.contains("return worldServer.pvpMode;"));
        Assert.assertTrue(behaviourText.contains("worldServer.pvpMode = pvp;"));
        Assert.assertTrue(behaviourText.contains("worldServer.setSpawnFlags(allowMonsters, allowAnimals);"));
        Assert.assertTrue(behaviourText.contains("return worldServer.allowAnimals;"));
        Assert.assertTrue(behaviourText.contains("return worldServer.allowMonsters;"));
        Assert.assertTrue(behaviourText.contains("return 128;"));
        Assert.assertTrue(behaviourText.contains("return worldServer.keepSpawnInMemory;"));
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
