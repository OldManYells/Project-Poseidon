package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Entity;
import net.minecraft.server.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.util.Vector;

/**
 * Canonical behaviour for CraftEntity transform/velocity/fall-distance bridge operations.
 */
public final class EntityTransformBridgeBehaviour {
    private static final EntityTransformBridgeBehaviour INSTANCE = new EntityTransformBridgeBehaviour();

    private EntityTransformBridgeBehaviour() {
    }

    public static EntityTransformBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Location getLocation(Entity entity) {
        return new Location(getWorld(entity), entity.locX, entity.locY, entity.locZ, entity.yaw, entity.pitch);
    }

    public Vector getVelocity(Entity entity) {
        return new Vector(entity.motX, entity.motY, entity.motZ);
    }

    public void setVelocity(Entity entity, Vector velocity) {
        entity.motX = velocity.getX();
        entity.motY = velocity.getY();
        entity.motZ = velocity.getZ();
        entity.velocityChanged = true;
    }

    public World getWorld(Entity entity) {
        return ((WorldServer) entity.world).getWorld();
    }

    public boolean teleport(Entity entity, Location destination) {
        entity.world = ((CraftWorld) destination.getWorld()).getHandle();
        entity.setLocation(destination.getX(), destination.getY(), destination.getZ(), destination.getYaw(), destination.getPitch());
        return true;
    }

    public float getFallDistance(Entity entity) {
        return entity.fallDistance;
    }

    public void setFallDistance(Entity entity, float distance) {
        entity.fallDistance = distance;
    }
}
