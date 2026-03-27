package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEntity transform/velocity/fall-distance bridge operations.
 */
public final class EntityTransformBridgeBehaviour {
    private static final EntityTransformBridgeBehaviour INSTANCE = new EntityTransformBridgeBehaviour();
    private static final WorldHandleBridgeBehaviour WORLD_HANDLE_BRIDGE_BEHAVIOUR =
            WorldHandleBridgeBehaviour.getInstance();
    private static final WorldServerProjectionBridgeBehaviour WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldServerProjectionBridgeBehaviour.getInstance();

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

    public Vector getMomentum(Entity entity) {
        return getVelocity(entity);
    }

    public void setVelocity(Entity entity, Vector velocity) {
        entity.motX = velocity.getX();
        entity.motY = velocity.getY();
        entity.motZ = velocity.getZ();
        entity.velocityChanged = true;
    }

    public void setMomentum(Entity entity, Vector momentum) {
        setVelocity(entity, momentum);
    }

    public World getWorld(Entity entity) {
        return WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(entity.world);
    }

    public boolean teleport(Entity entity, Location destination) {
        entity.world = WORLD_HANDLE_BRIDGE_BEHAVIOUR.resolveWorldServerHandle(destination.getWorld());
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
