package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftEntityWrapperThinnessTest {
    private static final Path CRAFT_ENTITY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/entity/CraftEntity.java");

    @Test
    public void craftEntityDelegatesNearbyAndPassengerBridgeLogicToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityInteractionBridgeBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_INTERACTION_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("collectNearbyEntities(entity, x, y, z)"));
        Assert.assertTrue(text.contains("setPassenger(getHandle(), passenger)"));
        Assert.assertFalse(text.contains("List<Entity> notchEntityList = entity.world.b(entity, entity.boundingBox.b(x, y, z));"));
        Assert.assertFalse(text.contains("if (passenger instanceof CraftEntity)"));
        Assert.assertFalse(text.contains("getHandle().passenger.setPassengerOf(null);"));
    }

    @Test
    public void craftEntityDelegatesWrapperTypeFactoryToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityWrapperFactoryBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_FACTORY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("createEntityWrapper("));
        Assert.assertFalse(text.contains("if (entity instanceof EntityLiving)"));
        Assert.assertFalse(text.contains("else if (entity instanceof EntityArrow)"));
        Assert.assertFalse(text.contains("else throw new IllegalArgumentException(\"Unknown entity\");"));
    }

    @Test
    public void craftEntityDelegatesTransformVelocityTeleportAndFallDistanceToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityTransformBridgeBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getLocation(entity)"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.teleport(entity, location)"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getFallDistance(getHandle())"));
        Assert.assertFalse(text.contains("return new Location(getWorld(), entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);"));
        Assert.assertFalse(text.contains("entity.motX = vel.getX();"));
        Assert.assertFalse(text.contains("entity.world = ((CraftWorld) location.getWorld()).getHandle();"));
    }

    @Test
    public void craftEntityDelegatesCoreStateFieldBridgesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityStateBridgeBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_STATE_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("getEntityId(entity)"));
        Assert.assertTrue(text.contains("setFireTicks(entity, ticks)"));
        Assert.assertTrue(text.contains("getUniqueId(getHandle())"));
        Assert.assertFalse(text.contains("return entity.id;"));
        Assert.assertFalse(text.contains("entity.dead = true;"));
        Assert.assertFalse(text.contains("return getHandle().uniqueId;"));
    }

    @Test
    public void craftEntityDelegatesIdentityAndPlayerWrapperCacheToCanonicalBehaviours() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityIdentityBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityPlayerWrapperCacheBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.equalsEntity(this, obj, server, entity)"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.hash(server, entity)"));
        Assert.assertTrue(text.contains("ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR.resolvePlayer(players, entity)"));
        Assert.assertFalse(text.contains("if (this.server != other.server && (this.server == null || !this.server.equals(other.server)))"));
        Assert.assertFalse(text.contains("hash = 89 * hash + (this.entity != null ? this.entity.hashCode() : 0);"));
        Assert.assertFalse(text.contains("result = new CraftPlayer((CraftServer) Bukkit.getServer(), entity);"));
    }
}
