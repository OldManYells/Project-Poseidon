package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory tame event construction.
 */
public final class EntityTameEventConstructionBehaviour {
    private static final EntityTameEventConstructionBehaviour INSTANCE = new EntityTameEventConstructionBehaviour();

    private EntityTameEventConstructionBehaviour() {
    }

    public static EntityTameEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public EntityTameEvent createEntityTameEvent(com.legacyminecraft.compat.bukkit.Entity tameable, AnimalTamer tamer) {
        return new EntityTameEvent(tameable, tamer);
    }
}
