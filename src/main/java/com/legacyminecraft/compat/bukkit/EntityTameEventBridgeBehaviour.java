package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for tame-event hook invocation.
 */
public final class EntityTameEventBridgeBehaviour {
    private static final EntityTameEventBridgeBehaviour INSTANCE = new EntityTameEventBridgeBehaviour();
    private static final EventFactoryLifecycleSystem EVENT_FACTORY_LIFECYCLE_SYSTEM =
            EventFactoryLifecycleSystem.getInstance();

    private EntityTameEventBridgeBehaviour() {
    }

    public static EntityTameEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isTamingAllowed(EntityLiving tameable, EntityHuman tamer) {
        return !EVENT_FACTORY_LIFECYCLE_SYSTEM.callEntityTameEvent(tameable, tamer).isCancelled();
    }
}
