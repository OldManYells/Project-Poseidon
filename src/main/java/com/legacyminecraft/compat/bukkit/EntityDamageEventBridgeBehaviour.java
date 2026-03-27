package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEntity last-damage-event bridge state.
 */
public final class EntityDamageEventBridgeBehaviour {
    private static final EntityDamageEventBridgeBehaviour INSTANCE = new EntityDamageEventBridgeBehaviour();

    private EntityDamageEventBridgeBehaviour() {
    }

    public static EntityDamageEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public EntityDamageEvent assignLastDamageEvent(EntityDamageEvent event) {
        return event;
    }

    public EntityDamageEvent resolveLastDamageEvent(EntityDamageEvent event) {
        return event;
    }
}
