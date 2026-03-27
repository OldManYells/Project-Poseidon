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
    private static final Path PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/ProjectileShooterBridgeBehaviour.java");

    @Test
    public void projectileWrappersDelegateShooterBridgeConversionsToCanonicalBehaviour() throws IOException {
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_ARROW_PATH, "EntityArrow");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_EGG_PATH, "EntityEgg");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_SNOWBALL_PATH, "EntitySnowball");
        assertProjectileWrapperDelegatesShooterBridge(CRAFT_FIREBALL_PATH, "EntityFireball");

        String behaviourText = new String(
                Files.readAllBytes(PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR_PATH),
                StandardCharsets.UTF_8
        );
        Assert.assertTrue(behaviourText.contains("EntityHandleBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("ENTITY_HANDLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(behaviourText.contains("resolveLivingHandle(shooter)"));
        Assert.assertFalse(behaviourText.contains("CraftLivingEntity"));
    }

    private void assertProjectileWrapperDelegatesShooterBridge(Path path, String entityTypeName) throws IOException {
        String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ProjectileShooterBridgeBehaviour"));
        Assert.assertTrue(text.contains("ProjectileShooterAssignmentBehaviour"));
        Assert.assertTrue(text.contains("ProjectileEntityShooterFieldBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(entity, " + entityTypeName + ".class)"));
        Assert.assertTrue(text.contains("toBukkitShooter("));
        Assert.assertTrue(text.contains("toNmsShooter("));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.toBukkitShooter("));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.assignShooter("));
        Assert.assertFalse(text.contains("(" + entityTypeName + ") getHandle()"));
        Assert.assertFalse(text.contains("((" + entityTypeName + ") getHandle()).shooter"));
        Assert.assertFalse(text.contains("((" + entityTypeName + ") getHandle()).thrower"));
        Assert.assertFalse(text.contains("shooter instanceof CraftLivingEntity"));
        Assert.assertFalse(text.contains("getBukkitEntity()"));
        Assert.assertFalse(text.contains("(EntityLiving) ((CraftLivingEntity) shooter).entity"));
        Assert.assertFalse(text.contains("if (shooterEntity != null) {"));
    }
}
