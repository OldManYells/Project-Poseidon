package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftEntityVehicleMiscWrapperThinnessTest {
    private static final Path ABSTRACT_PROJECTILE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/AbstractProjectile.java");
    private static final Path CRAFT_VEHICLE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftVehicle.java");
    private static final Path CRAFT_BOAT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftBoat.java");
    private static final Path CRAFT_ARROW_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftArrow.java");
    private static final Path CRAFT_TNT_PRIMED_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftTNTPrimed.java");
    private static final Path CRAFT_MINECART_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftMinecart.java");
    private static final Path CRAFT_FISH_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFish.java");
    private static final Path CRAFT_FIREBALL_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFireball.java");
    private static final Path CRAFT_SNOWBALL_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSnowball.java");
    private static final Path CRAFT_POWERED_MINECART_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPoweredMinecart.java");
    private static final Path CRAFT_STORAGE_MINECART_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftStorageMinecart.java");
    private static final Path CRAFT_ITEM_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftItem.java");
    private static final Path CRAFT_EGG_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftEgg.java");
    private static final Path CRAFT_WATER_MOB_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftWaterMob.java");
    private static final Path CRAFT_FLYING_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFlying.java");
    private static final Path CRAFT_GHAST_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftGhast.java");
    private static final Path CRAFT_SQUID_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSquid.java");
    private static final Path CRAFT_WEATHER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftWeather.java");
    private static final Path CRAFT_FALLING_SAND_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFallingSand.java");
    private static final Path CRAFT_PAINTING_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftPainting.java");
    private static final Path ENTITY_WRAPPER_STRING_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityWrapperStringBehaviour.java");
    private static final Path PROJECTILE_BOUNCE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/ProjectileBounceBehaviour.java");
    private static final Path ENTITY_WEATHER_HANDLE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityWeatherHandleBehaviour.java");
    private static final Path ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityWrapperDescriptionBehaviour.java");

    @Test
    public void abstractProjectileDelegatesBounceStateToCanonicalBehaviour() throws IOException {
        String wrapperText = read(ABSTRACT_PROJECTILE_PATH);
        String behaviourText = read(PROJECTILE_BOUNCE_BEHAVIOUR_PATH);

        Assert.assertTrue(wrapperText.contains("ProjectileBounceBehaviour"));
        Assert.assertTrue(wrapperText.contains("PROJECTILE_BOUNCE_BEHAVIOUR"));
        Assert.assertTrue(wrapperText.contains("doesBounce(this)"));
        Assert.assertTrue(wrapperText.contains("setBounce(this, doesBounce)"));
        Assert.assertFalse(wrapperText.contains("private boolean doesBounce;"));
        Assert.assertFalse(wrapperText.contains("doesBounce = false;"));
        Assert.assertFalse(wrapperText.contains("return doesBounce;"));
        Assert.assertFalse(wrapperText.contains("this.doesBounce = doesBounce;"));

        Assert.assertTrue(behaviourText.contains("IdentityHashMap"));
        Assert.assertTrue(behaviourText.contains("doesBounce(AbstractProjectile projectile)"));
        Assert.assertTrue(behaviourText.contains("setBounce(AbstractProjectile projectile, boolean doesBounce)"));
        Assert.assertTrue(behaviourText.contains("bounceStates.containsKey(projectile)"));
    }

    @Test
    public void vehicleAndMiscWrappersDelegateToCanonicalStringBehaviour() throws IOException {
        String behaviourText = read(ENTITY_WRAPPER_STRING_BEHAVIOUR_PATH);
        String descriptionBehaviourText = read(ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR_PATH);

        assertWrapperStringDelegates(CRAFT_VEHICLE_PATH, "CraftVehicle", "toString(\"CraftVehicle\", getPassenger())");
        assertWrapperStringDelegates(CRAFT_BOAT_PATH, "CraftBoat", "toString(\"CraftBoat\")");
        assertWrapperStringDelegates(CRAFT_ARROW_PATH, "CraftArrow", "toString(\"CraftArrow\")");
        assertWrapperStringDelegates(CRAFT_TNT_PRIMED_PATH, "CraftTNTPrimed", "toString(\"CraftTNTPrimed\")");
        assertWrapperStringDelegates(CRAFT_MINECART_PATH, "CraftMinecart", "toString(\"CraftMinecart\")");
        assertWrapperStringDelegates(CRAFT_FISH_PATH, "CraftFish", "toString(\"CraftFish\")");
        assertWrapperStringDelegates(CRAFT_FIREBALL_PATH, "CraftFireball", "toString(\"CraftFireball\")");
        assertWrapperStringDelegates(CRAFT_SNOWBALL_PATH, "CraftSnowball", "toString(\"CraftSnowball\")");
        assertWrapperStringDelegates(CRAFT_POWERED_MINECART_PATH, "CraftPoweredMinecart", "toString(\"CraftPoweredMinecart\")");
        assertWrapperStringDelegates(CRAFT_ITEM_PATH, "CraftItem", "toString(\"CraftItem\")");
        assertWrapperStringDelegates(CRAFT_EGG_PATH, "CraftEgg", "toString(\"CraftEgg\")");
        assertWrapperStringDelegates(CRAFT_WATER_MOB_PATH, "CraftWaterMob", "toString(\"CraftWaterMob\")");
        assertWrapperStringDelegates(CRAFT_FLYING_PATH, "CraftFlying", "toString(\"CraftFlying\")");
        assertWrapperStringDelegates(CRAFT_GHAST_PATH, "CraftGhast", "toString(\"CraftGhast\")");
        assertWrapperStringDelegates(CRAFT_SQUID_PATH, "CraftSquid", "toString(\"CraftSquid\")");
        assertWrapperStringDelegates(CRAFT_FALLING_SAND_PATH, "CraftFallingSand", "toString(\"CraftFallingSand\")");
        assertWrapperStringDelegates(CRAFT_PAINTING_PATH, "CraftPainting", "toString(\"CraftPainting\")");
        assertStorageMinecartDescriptionDelegates(CRAFT_STORAGE_MINECART_PATH);

        Assert.assertTrue(behaviourText.contains("EntityWrapperStringBehaviour"));
        Assert.assertTrue(behaviourText.contains("toString(String wrapperName)"));
        Assert.assertTrue(behaviourText.contains("toString(String wrapperName, Entity passenger)"));
        Assert.assertTrue(behaviourText.contains("wrapperName + \"{passenger=\" + passenger + '}'"));
        Assert.assertTrue(descriptionBehaviourText.contains("craftStorageMinecartToString(Object inventory)"));
    }

    @Test
    public void craftWeatherDelegatesTypedHandleResolutionToCanonicalBehaviour() throws IOException {
        String wrapperText = read(CRAFT_WEATHER_PATH);
        String behaviourText = read(ENTITY_WEATHER_HANDLE_BEHAVIOUR_PATH);

        Assert.assertTrue(wrapperText.contains("EntityWeatherHandleBehaviour"));
        Assert.assertTrue(wrapperText.contains("ENTITY_WEATHER_HANDLE_BEHAVIOUR"));
        Assert.assertTrue(wrapperText.contains("resolveHandle(super.getHandle())"));
        Assert.assertFalse(wrapperText.contains("return (EntityWeather) super.getHandle();"));

        Assert.assertTrue(behaviourText.contains("EntityWeatherHandleBehaviour"));
        Assert.assertTrue(behaviourText.contains("resolveHandle(Entity entity)"));
        Assert.assertTrue(behaviourText.contains("return (EntityWeather) entity;"));
    }

    private void assertWrapperStringDelegates(Path path, String wrapperName, String callSnippet) throws IOException {
        String text = read(path);

        Assert.assertTrue(text.contains("EntityWrapperStringBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_STRING_BEHAVIOUR"));
        Assert.assertTrue(text.contains(callSnippet));
        Assert.assertFalse(text.contains("return \"" + wrapperName + "\";"));
        Assert.assertFalse(text.contains("return \"" + wrapperName + "{passenger=\" + getPassenger() + '}';"));
    }

    private void assertStorageMinecartDescriptionDelegates(Path path) throws IOException {
        String text = read(path);

        Assert.assertTrue(text.contains("EntityWrapperDescriptionBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("craftStorageMinecartToString(inventory)"));
        Assert.assertFalse(text.contains("return \"CraftStorageMinecart{\" + \"inventory=\" + inventory + '}';"));
    }

    private String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
