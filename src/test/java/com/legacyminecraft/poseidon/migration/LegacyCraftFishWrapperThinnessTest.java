package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftFishWrapperThinnessTest {
    private static final Path CRAFT_FISH_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftFish.java");
    private static final Path FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/FishHookOwnerBridgeBehaviour.java");

    @Test
    public void craftFishDelegatesOwnerConversionToCanonicalBridgeBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FISH_PATH), StandardCharsets.UTF_8);
        String behaviourText = new String(
                Files.readAllBytes(FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR_PATH),
                StandardCharsets.UTF_8
        );

        Assert.assertTrue(text.contains("FishHookOwnerBridgeBehaviour"));
        Assert.assertTrue(text.contains("ProjectileShooterAssignmentBehaviour"));
        Assert.assertTrue(text.contains("ProjectileEntityShooterFieldBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("FISH_HOOK_OWNER_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PROJECTILE_ENTITY_SHOOTER_FIELD_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityFish.class)"));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.toBukkitShooter("));
        Assert.assertTrue(text.contains("PROJECTILE_SHOOTER_ASSIGNMENT_BEHAVIOUR.assignShooter("));
        Assert.assertTrue(text.contains("toBukkitOwner("));
        Assert.assertTrue(text.contains("toNmsOwner("));
        Assert.assertFalse(text.contains("(EntityFish) getHandle()"));
        Assert.assertFalse(text.contains("((EntityFish) getHandle()).owner"));
        Assert.assertFalse(text.contains("if (((EntityFish) getHandle()).owner != null)"));
        Assert.assertFalse(text.contains("owner.getBukkitEntity()"));
        Assert.assertFalse(text.contains("shooter instanceof CraftHumanEntity"));
        Assert.assertFalse(text.contains("(EntityHuman) ((CraftHumanEntity) shooter).entity"));
        Assert.assertFalse(text.contains("if (owner != null) {"));

        Assert.assertTrue(behaviourText.contains("EntityHandleBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("ENTITY_HANDLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(behaviourText.contains("resolveHumanHandle(shooter)"));
        Assert.assertFalse(behaviourText.contains("CraftHumanEntity"));
    }
}
