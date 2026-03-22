package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.entity.EntityTameEvent;

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

    public EntityTameEvent createEntityTameEvent(org.bukkit.entity.Entity tameable, AnimalTamer tamer) {
        return new EntityTameEvent(tameable, tamer);
    }
}
