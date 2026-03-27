package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for resolving NMS handles from Bukkit entities.
 */
public final class EntityHandleBridgeBehaviour {
    private static final EntityHandleBridgeBehaviour INSTANCE = new EntityHandleBridgeBehaviour();
    private static final EntityBukkitProjectionBridgeBehaviour ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR =
            EntityBukkitProjectionBridgeBehaviour.getInstance();

    private EntityHandleBridgeBehaviour() {
    }

    public static EntityHandleBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveHandle(Object bukkitEntity) {
        if (bukkitEntity == null) {
            return null;
        }
        Object craftEntity = ENTITY_BUKKIT_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftEntity(BridgeReflection.cast(bukkitEntity));
        if (craftEntity == null) {
            return null;
        }
        return BridgeReflection.cast(BridgeReflection.invoke(craftEntity, "getHandle"));
    }

    public <T> T resolveLivingHandle(Object livingEntity) {
        Entity handle = resolveHandle(livingEntity);
        if (handle instanceof EntityLiving) {
            return BridgeReflection.cast(handle);
        }
        return null;
    }

    public <T> T resolveHumanHandle(Object livingEntity) {
        Entity handle = resolveHandle(livingEntity);
        if (handle instanceof EntityHuman) {
            return BridgeReflection.cast(handle);
        }
        return null;
    }

    public <T> T resolveAnimalHandle(Object entity) {
        return BridgeReflection.cast(entity);
    }

    public <T> T resolveMonsterHandle(Object entity) {
        return BridgeReflection.cast(entity);
    }
}
