package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat entity-type resolver scaffold.
 */
public final class EntityTypes {
    private EntityTypes() {
    }

    public static int a(EntityLiving entityLiving) {
        return entityLiving == null ? 0 : entityLiving.getClass().getName().hashCode();
    }

    public static Entity a(String entityName, WorldServer worldServer) {
        return new EntityLiving();
    }
}
