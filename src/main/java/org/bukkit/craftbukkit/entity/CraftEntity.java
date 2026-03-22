package org.bukkit.craftbukkit.entity;

import com.google.common.collect.MapMaker;
import com.legacyminecraft.poseidon.compat.bukkit.EntityInteractionBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityIdentityBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityPlayerWrapperCacheBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityStateBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityTransformBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityWrapperFactoryBehaviour;
import net.minecraft.server.*;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class CraftEntity implements org.bukkit.entity.Entity {
    private static final Map<String, CraftPlayer> players = new MapMaker().softValues().makeMap();
    private static final EntityInteractionBridgeBehaviour ENTITY_INTERACTION_BRIDGE_BEHAVIOUR =
            EntityInteractionBridgeBehaviour.getInstance();
    private static final EntityTransformBridgeBehaviour ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR =
            EntityTransformBridgeBehaviour.getInstance();
    private static final EntityStateBridgeBehaviour ENTITY_STATE_BRIDGE_BEHAVIOUR =
            EntityStateBridgeBehaviour.getInstance();
    private static final EntityWrapperFactoryBehaviour ENTITY_WRAPPER_FACTORY_BEHAVIOUR =
            EntityWrapperFactoryBehaviour.getInstance();
    private static final EntityIdentityBridgeBehaviour ENTITY_IDENTITY_BRIDGE_BEHAVIOUR =
            EntityIdentityBridgeBehaviour.getInstance();
    private static final EntityPlayerWrapperCacheBehaviour ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR =
            EntityPlayerWrapperCacheBehaviour.getInstance();
    protected final CraftServer server;
    protected Entity entity;
    private EntityDamageEvent lastDamageEvent;

    public CraftEntity(final CraftServer server, final Entity entity) {
        this.server = server;
        this.entity = entity;
    }

    public static CraftEntity getEntity(CraftServer server, Entity entity) {
        return ENTITY_WRAPPER_FACTORY_BEHAVIOUR.createEntityWrapper(
                server,
                entity,
                new EntityWrapperFactoryBehaviour.PlayerResolver() {
                    public CraftPlayer resolvePlayer(EntityPlayer entityPlayer) {
                        return getPlayer(entityPlayer);
                    }
                }
        );
    }

    public Location getLocation() {
        return ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getLocation(entity);
    }

    public Vector getVelocity() {
        return ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getVelocity(entity);
    }

    public void setVelocity(Vector vel) {
        ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.setVelocity(entity, vel);
    }

    public World getWorld() {
        return ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getWorld(entity);
    }

    public boolean teleport(Location location) {
        return ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.teleport(entity, location);
    }

    public boolean teleport(org.bukkit.entity.Entity destination) {
        return teleport(destination.getLocation());
    }

    public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        return ENTITY_INTERACTION_BRIDGE_BEHAVIOUR.collectNearbyEntities(entity, x, y, z);
    }

    public int getEntityId() {
        return ENTITY_STATE_BRIDGE_BEHAVIOUR.getEntityId(entity);
    }

    public int getFireTicks() {
        return ENTITY_STATE_BRIDGE_BEHAVIOUR.getFireTicks(entity);
    }

    public int getMaxFireTicks() {
        return ENTITY_STATE_BRIDGE_BEHAVIOUR.getMaxFireTicks(entity);
    }

    public void setFireTicks(int ticks) {
        ENTITY_STATE_BRIDGE_BEHAVIOUR.setFireTicks(entity, ticks);
    }

    public void remove() {
        ENTITY_STATE_BRIDGE_BEHAVIOUR.remove(entity);
    }

    public boolean isDead() {
        return ENTITY_STATE_BRIDGE_BEHAVIOUR.isDead(entity);
    }

    public Entity getHandle() {
        return entity;
    }

    public void setHandle(final Entity entity) {
        this.entity = entity;
    }

    @Override
    public boolean equals(Object obj) {
        return ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.equalsEntity(this, obj, server, entity);
    }

    @Override
    public int hashCode() {
        return ENTITY_IDENTITY_BRIDGE_BEHAVIOUR.hash(server, entity);
    }

    @Override
    public String toString() {
        return "CraftEntity{" + "id=" + getEntityId() + '}';
    }

    public Server getServer() {
        return server;
    }

    public Vector getMomentum() {
        return getVelocity();
    }

    public void setMomentum(Vector value) {
        setVelocity(value);
    }

    public org.bukkit.entity.Entity getPassenger() {
        return ENTITY_INTERACTION_BRIDGE_BEHAVIOUR.getPassenger(getHandle());
    }

    public boolean setPassenger(org.bukkit.entity.Entity passenger) {
        return ENTITY_INTERACTION_BRIDGE_BEHAVIOUR.setPassenger(getHandle(), passenger);
    }

    public boolean isEmpty() {
        return ENTITY_INTERACTION_BRIDGE_BEHAVIOUR.isPassengerSlotEmpty(getHandle());
    }

    public boolean eject() {
        return ENTITY_INTERACTION_BRIDGE_BEHAVIOUR.ejectPassenger(getHandle());
    }

    public float getFallDistance() {
        return ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.getFallDistance(getHandle());
    }

    public void setFallDistance(float distance) {
        ENTITY_TRANSFORM_BRIDGE_BEHAVIOUR.setFallDistance(getHandle(), distance);
    }

    public void setLastDamageCause(EntityDamageEvent event) {
        lastDamageEvent = event;
    }

    public EntityDamageEvent getLastDamageCause() {
        return lastDamageEvent;
    }

    public UUID getUniqueId() {
        return ENTITY_STATE_BRIDGE_BEHAVIOUR.getUniqueId(getHandle());
    }
    private static CraftPlayer getPlayer(EntityPlayer entity) {
        return ENTITY_PLAYER_WRAPPER_CACHE_BEHAVIOUR.resolvePlayer(players, entity);
    }
}
