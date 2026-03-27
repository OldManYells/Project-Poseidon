package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting NMS entities to Bukkit wrapper views.
 */
public final class EntityBukkitProjectionBridgeBehaviour {
    private static final EntityBukkitProjectionBridgeBehaviour INSTANCE = new EntityBukkitProjectionBridgeBehaviour();

    private EntityBukkitProjectionBridgeBehaviour() {
    }

    public static EntityBukkitProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftLivingEntity resolveCraftLivingEntity(EntityLiving entityLiving) {
        if (entityLiving == null) {
            return null;
        }

        Entity bukkitEntity = entityLiving.getBukkitEntity();
        if (bukkitEntity instanceof CraftLivingEntity) {
            return (CraftLivingEntity) bukkitEntity;
        }
        return null;
    }

    public CraftEntity resolveCraftEntity(Entity bukkitEntity) {
        if (bukkitEntity instanceof CraftEntity) {
            return (CraftEntity) bukkitEntity;
        }
        return null;
    }
}
