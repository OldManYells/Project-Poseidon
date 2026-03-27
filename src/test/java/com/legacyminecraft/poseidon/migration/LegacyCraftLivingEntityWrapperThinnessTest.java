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
        Assert.assertTrue(text.contains("collectLineOfSight(this, transparent, maxDistance, 0)"));
        Assert.assertTrue(text.contains("resolveTargetBlock(this, transparent, maxDistance)"));
        Assert.assertTrue(text.contains("collectLastTargetBlocks(this, transparent, maxDistance, 2)"));
        Assert.assertFalse(text.contains("if (maxDistance > 120)"));
        Assert.assertFalse(text.contains("Iterator<Block> itr = new BlockIterator(this, maxDistance);"));
        Assert.assertFalse(text.contains("if (!transparent.contains((byte) id))"));
        Assert.assertFalse(text.contains("List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);"));
        Assert.assertFalse(text.contains("return getLineOfSight(transparent, maxDistance, 2);"));
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
        Assert.assertTrue(text.contains("LivingEntityContextBridgeBehaviour"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_PROJECTILE_LAUNCH_BEHAVIOUR"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_CONTEXT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("resolveWorldHandle(getWorld())"));
        Assert.assertTrue(text.contains("throwEgg(world, getHandle())"));
        Assert.assertTrue(text.contains("throwSnowball(world, getHandle())"));
        Assert.assertTrue(text.contains("shootArrow(world, getHandle())"));
        Assert.assertFalse(text.contains("EntityEgg egg = new EntityEgg(world, getHandle());"));
        Assert.assertFalse(text.contains("EntitySnowball snowball = new EntitySnowball(world, getHandle());"));
        Assert.assertFalse(text.contains("EntityArrow arrow = new EntityArrow(world, getHandle());"));
        Assert.assertFalse(text.contains("((CraftWorld) getWorld()).getHandle()"));
    }

    @Test
    public void craftLivingEntityDelegatesDamageAndAirStatePolicyToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("LivingEntityDamageStateBehaviour"));
        Assert.assertTrue(text.contains("resolveDamageSourceHandle(source)"));
        Assert.assertTrue(text.contains("LIVING_ENTITY_DAMAGE_STATE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("applyDamage(getHandle(), amount)"));
        Assert.assertTrue(text.contains("setMaximumAir(getHandle(), ticks)"));
        Assert.assertFalse(text.contains("entity.damageEntity((Entity) null, amount);"));
        Assert.assertFalse(text.contains("getHandle().airTicks = ticks;"));
        Assert.assertFalse(text.contains("getHandle().noDamageTicks = ticks;"));
        Assert.assertFalse(text.contains("((CraftEntity) source).getHandle()"));
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

    @Test
    public void craftLivingEntityDelegatesToStringDescriptorRenderingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_LIVING_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityWrapperDescriptionBehaviour"));
        Assert.assertTrue(text.contains("EntityTypedHandleCastBehaviour"));
        Assert.assertTrue(text.contains("EntityHandleMutationBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TYPED_HANDLE_CAST_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_HANDLE_MUTATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("castHandle(entity, EntityLiving.class)"));
        Assert.assertTrue(text.contains("ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks()"));
        Assert.assertTrue(text.contains("craftLivingEntityToString(getEntityId())"));
        Assert.assertFalse(text.contains("return (EntityLiving) entity;"));
        Assert.assertFalse(text.contains("super.setHandle((Entity) entity);"));
        Assert.assertFalse(text.contains("this.entity = entity;"));
        Assert.assertFalse(text.contains("return \"CraftLivingEntity{\" + \"id=\" + getEntityId() + '}';"));
    }
}
