package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftLivingEntityWrapperThinnessTest {
    private static final Path CRAFT_LIVING_ENTITY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftLivingEntity.java");

    @Test
    public void craftLivingEntityDelegatesLineOfSightPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityTargetingBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_TARGETING_BEHAVIOUR"));
        Assert.assertTrue(text.contains("collectLineOfSight(this, transparent, maxDistance, maxLength)"));
        Assert.assertFalse(text.contains("if (maxDistance > 120)"));
        Assert.assertFalse(text.contains("Iterator<Block> itr = new BlockIterator(this, maxDistance);"));
        Assert.assertFalse(text.contains("if (!transparent.contains((byte) id))"));
    }

    @Test
    public void craftLivingEntityDelegatesVehicleBridgePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityVehicleBridgeBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_VEHICLE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveVehicle(getHandle())"));
        Assert.assertFalse(text.contains("if (getHandle().vehicle == null)"));
        Assert.assertFalse(text.contains("getHandle().setPassengerOf(null);"));
        Assert.assertFalse(text.contains("vehicle instanceof Vehicle"));
    }

    @Test
    public void craftLivingEntityDelegatesProjectileLaunchPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityProjectileLaunchBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR"));
        Assert.assertTrue(text.contains("throwEgg(world, getHandle())"));
        Assert.assertTrue(text.contains("throwSnowball(world, getHandle())"));
        Assert.assertTrue(text.contains("shootArrow(world, getHandle())"));
        Assert.assertFalse(text.contains("EntityEgg egg = new EntityEgg(world, getHandle());"));
        Assert.assertFalse(text.contains("EntitySnowball snowball = new EntitySnowball(world, getHandle());"));
        Assert.assertFalse(text.contains("EntityArrow arrow = new EntityArrow(world, getHandle());"));
    }

    @Test
    public void craftLivingEntityDelegatesDamageAndAirStatePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityDamageStateBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("applyDamage(getHandle(), amount)"));
        Assert.assertTrue(text.contains("setMaximumAir(getHandle(), ticks)"));
        Assert.assertFalse(text.contains("entity.damageEntity((Entity) null, amount);"));
        Assert.assertFalse(text.contains("getHandle().airTicks = ticks;"));
        Assert.assertFalse(text.contains("getHandle().noDamageTicks = ticks;"));
    }

    @Test
    public void craftLivingEntityDelegatesHealthAndEyeViewPolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityHealthAndViewBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_HEALTH_AND_VIEW_BEHAVIOUR"));
        Assert.assertTrue(text.contains("setHealth(getHandle(), health)"));
        Assert.assertTrue(text.contains("computeEyeLocation(getLocation(), getEyeHeight())"));
        Assert.assertFalse(text.contains("if ((health < 0) || (health > 200))"));
        Assert.assertFalse(text.contains("((EntityPlayer) entity).die((Entity) null);"));
        Assert.assertFalse(text.contains("loc.setY(loc.getY() + getEyeHeight());"));
    }
}
