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
    private static final Path ENTITY_IDENTITY_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityIdentityBridgeBehaviour.java");
    private static final Path ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityPlayerWrapperCacheBehaviour.java");
    private static final Path ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityTransformBridgeBehaviour.java");

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
        String entityTransformBridgeText =
                new String(Files.readAllBytes(ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityTransformBridgeBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getLocation(entity)"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.teleport(entity, location)"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getFallDistance(getHandle())"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getMomentum(getHandle())"));
        Assert.assertTrue(text.contains("ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.setMomentum(getHandle(), value)"));
        Assert.assertFalse(text.contains("return new Location(getWorld(), entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);"));
        Assert.assertFalse(text.contains("entity.motX = vel.getX();"));
        Assert.assertFalse(text.contains("entity.world = ((CraftWorld) location.getWorld()).getHandle();"));
        Assert.assertFalse(text.contains("return getVelocity();"));
        Assert.assertFalse(text.contains("setVelocity(value);"));
        Assert.assertTrue(entityTransformBridgeText.contains("WorldServerProjectionBridgeBehaviour"));
        Assert.assertTrue(entityTransformBridgeText.contains("WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(entity.world)"));
        Assert.assertFalse(entityTransformBridgeText.contains("return ((WorldServer) entity.world).getWorld();"));
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
        String entityIdentityBridgeText =
                new String(Files.readAllBytes(ENTITY_IDENTITY_BRIDGE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);
        String entityPlayerWrapperCacheText =
                new String(Files.readAllBytes(ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityIdentityBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityPlayerWrapperCacheBehaviour"));
        Assert.assertTrue(text.contains("EntityContextAccessBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityDamageEventBridgeBehaviour"));
        Assert.assertTrue(text.contains("EntityHandleMutationBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_CONTEXT_ACCESS_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_DAMAGE_EVENT_BRIDGE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_HANDLE_MUTATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.equalsEntity(this, obj, server, entity)"));
        Assert.assertTrue(text.contains("ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.hash(server, entity)"));
        Assert.assertTrue(text.contains("ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR.resolvePlayer(players, entity)"));
        Assert.assertTrue(text.contains("ENTITY_CONTEXT_ACCESS_BRIDGE_BEHAVIOUR.resolveHandle(entity)"));
        Assert.assertTrue(text.contains("ENTITY_CONTEXT_ACCESS_BRIDGE_BEHAVIOUR.resolveServer(server)"));
        Assert.assertTrue(text.contains("ENTITY_DAMAGE_EVENT_BRIDGE_BEHAVIOUR.assignLastDamageEvent(event)"));
        Assert.assertTrue(text.contains("ENTITY_DAMAGE_EVENT_BRIDGE_BEHAVIOUR.resolveLastDamageEvent(lastDamageEvent)"));
        Assert.assertTrue(text.contains("ENTITY_HANDLE_MUTATION_BEHAVIOUR.applyHandle(entity, new EntityHandleMutationBehaviour.HandleMutationCallbacks()"));
        Assert.assertTrue(text.contains("CraftEntity.this.entity = (Entity) updatedHandle;"));
        Assert.assertTrue(text.contains("setHandle(entity);"));
        Assert.assertFalse(text.contains("if (this.server != other.server && (this.server == null || !this.server.equals(other.server)))"));
        Assert.assertFalse(text.contains("hash = 89 * hash + (this.entity != null ? this.entity.hashCode() : 0);"));
        Assert.assertFalse(text.contains("result = new CraftPlayer((CraftServer) Bukkit.getServer(), entity);"));
        Assert.assertFalse(text.contains("return entity;"));
        Assert.assertFalse(text.contains("return server;"));
        Assert.assertFalse(text.contains("lastDamageEvent = event;"));
        Assert.assertFalse(text.contains("return lastDamageEvent;"));
        Assert.assertFalse(text.contains("this.entity = entity;"));
        Assert.assertTrue(entityIdentityBridgeText.contains("EntityBukkitProjectionBridgeBehaviour"));
        Assert.assertTrue(entityIdentityBridgeText.contains("ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftEntity((org.bukkit.entity.Entity) otherObject)"));
        Assert.assertFalse(entityIdentityBridgeText.contains("CraftEntity other = (CraftEntity) otherObject;"));
        Assert.assertTrue(entityPlayerWrapperCacheText.contains("ServerWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(entityPlayerWrapperCacheText.contains("SERVER_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftServer(Bukkit.getServer())"));
        Assert.assertFalse(entityPlayerWrapperCacheText.contains("new CraftPlayer((CraftServer) Bukkit.getServer(), entityPlayer)"));
    }

    @Test
    public void craftEntityDelegatesToStringDescriptorRenderingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_ENTITY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("EntityWrapperDescriptionBehaviour"));
        Assert.assertTrue(text.contains("ENTITY_WRAPPER_DESCRIPTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("craftEntityToString(getEntityId())"));
        Assert.assertFalse(text.contains("return \"CraftEntity{\" + \"id=\" + getEntityId() + '}';"));
    }
}
