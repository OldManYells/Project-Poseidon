package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftVehicleWrapperThinnessTest {
    private static final Path CRAFT_BOAT_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftBoat.java");
    private static final Path CRAFT_MINECART_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftMinecart.java");

    @Test
    public void craftBoatDelegatesPropertyAccessToCanonicalVehicleBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BOAT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("VehicleEntityPropertyBehaviour"));
        Assert.assertTrue(text.contains("VEHICLE_ENTITY_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBoatMaxSpeed(boat)"));
        Assert.assertTrue(text.contains("setBoatMaxSpeed(boat, speed)"));
        Assert.assertFalse(text.contains("return boat.maxSpeed;"));
        Assert.assertFalse(text.contains("boat.maxSpeed = speed;"));
    }

    @Test
    public void craftMinecartDelegatesPropertyAndVelocityAccessToCanonicalVehicleBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_MINECART_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("VehicleEntityPropertyBehaviour"));
        Assert.assertTrue(text.contains("VEHICLE_ENTITY_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setMinecartDamage(minecart, damage)"));
        Assert.assertTrue(text.contains("getMinecartDamage(minecart)"));
        Assert.assertTrue(text.contains("getMinecartMaxSpeed(minecart)"));
        Assert.assertTrue(text.contains("setMinecartMaxSpeed(minecart, speed)"));
        Assert.assertTrue(text.contains("isMinecartSlowWhenEmpty(minecart)"));
        Assert.assertTrue(text.contains("setMinecartSlowWhenEmpty(minecart, slow)"));
        Assert.assertTrue(text.contains("getMinecartFlyingVelocityMod(minecart)"));
        Assert.assertTrue(text.contains("setMinecartFlyingVelocityMod(minecart, flying)"));
        Assert.assertTrue(text.contains("getMinecartDerailedVelocityMod(minecart)"));
        Assert.assertTrue(text.contains("setMinecartDerailedVelocityMod(minecart, derailed)"));
        Assert.assertFalse(text.contains("minecart.damage = damage;"));
        Assert.assertFalse(text.contains("return minecart.damage;"));
        Assert.assertFalse(text.contains("return minecart.maxSpeed;"));
        Assert.assertFalse(text.contains("minecart.maxSpeed = speed;"));
        Assert.assertFalse(text.contains("return minecart.slowWhenEmpty;"));
        Assert.assertFalse(text.contains("minecart.slowWhenEmpty = slow;"));
        Assert.assertFalse(text.contains("new Vector(minecart.flyingX, minecart.flyingY, minecart.flyingZ)"));
        Assert.assertFalse(text.contains("minecart.derailedX = derailed.getX();"));
    }
}

