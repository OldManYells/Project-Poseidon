package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftProjectileWrapperThinnessTest {
    private static final Path CRAFT_ARROW_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftArrow.java");
    private static final Path CRAFT_EGG_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftEgg.java");
    private static final Path CRAFT_SNOWBALL_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftSnowball.java");
    private static final Path CRAFT_FIREBALL_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFireball.java");

    @Test
    public void projectileWrappersDelegateShooterBridgeConversionsToCanonicalBehaviour() throws IOException {
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_ARROW_PATH, "EntityArrow");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_EGG_PATH, "EntityEgg");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_SNOWBALL_PATH, "EntitySnowball");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_FIREBALL_PATH, "EntityFireball");
    }

    private void assertProjectileWrapperDelegatesShooterBridge(Path path, String entityTypeName) throws IOException {
        String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ProjectileShooterBridgeBehaviour"));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("toBukkitShooter((" + entityTypeName + ") getHandle())")
                || text.contains("toBukkitShooter(((" + entityTypeName + ") getHandle())"));
        Assert.assertTrue(text.contains("toNmsShooter(shooter)"));
        Assert.assertFalse(text.contains("shooter instanceof CraftLivingEntity"));
        Assert.assertFalse(text.contains("getBukkitEntity()"));
        Assert.assertFalse(text.contains("(EntityLiving) ((CraftLivingEntity) shooter).entity"));
    }
}

