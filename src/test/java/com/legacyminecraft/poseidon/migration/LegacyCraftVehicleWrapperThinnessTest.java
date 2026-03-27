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
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("VEHICLE_ENTITY_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getBoatMaxSpeed(getBoatHandle())"));
        Assert.assertTrue(text.contains("setBoatMaxSpeed(getBoatHandle(), speed)"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityBoat.class)"));
        Assert.assertFalse(text.contains("return boat.maxSpeed;"));
        Assert.assertFalse(text.contains("boat.maxSpeed = speed;"));
        Assert.assertFalse(text.contains("protected EntityBoat boat;"));
        Assert.assertFalse(text.contains("boat = entity;"));
    }

    @Test
    public void craftMinecartDelegatesPropertyAndVelocityAccessToCanonicalVehicleBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_MINECART_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("VehicleEntityPropertyBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("VEHICLE_ENTITY_PROPERTY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setMinecartDamage(getMinecartHandle(), damage)"));
        Assert.assertTrue(text.contains("getMinecartDamage(getMinecartHandle())"));
        Assert.assertTrue(text.contains("getMinecartMaxSpeed(getMinecartHandle())"));
        Assert.assertTrue(text.contains("setMinecartMaxSpeed(getMinecartHandle(), speed)"));
        Assert.assertTrue(text.contains("isMinecartSlowWhenEmpty(getMinecartHandle())"));
        Assert.assertTrue(text.contains("setMinecartSlowWhenEmpty(getMinecartHandle(), slow)"));
        Assert.assertTrue(text.contains("getMinecartFlyingVelocityMod(getMinecartHandle())"));
        Assert.assertTrue(text.contains("setMinecartFlyingVelocityMod(getMinecartHandle(), flying)"));
        Assert.assertTrue(text.contains("getMinecartDerailedVelocityMod(getMinecartHandle())"));
        Assert.assertTrue(text.contains("setMinecartDerailedVelocityMod(getMinecartHandle(), derailed)"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityMinecart.class)"));
        Assert.assertFalse(text.contains("minecart.damage = damage;"));
        Assert.assertFalse(text.contains("return minecart.damage;"));
        Assert.assertFalse(text.contains("return minecart.maxSpeed;"));
        Assert.assertFalse(text.contains("minecart.maxSpeed = speed;"));
        Assert.assertFalse(text.contains("return minecart.slowWhenEmpty;"));
        Assert.assertFalse(text.contains("minecart.slowWhenEmpty = slow;"));
        Assert.assertFalse(text.contains("new Vector(minecart.flyingX, minecart.flyingY, minecart.flyingZ)"));
        Assert.assertFalse(text.contains("minecart.derailedX = derailed.getX();"));
        Assert.assertFalse(text.contains("protected EntityMinecart minecart;"));
        Assert.assertFalse(text.contains("minecart = entity;"));
    }
}
