package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for world entity spawn-cancellation checks.
 */
public final class WorldEntitySpawnEventBridgeBehaviour {
    private static final WorldEntitySpawnEventBridgeBehaviour INSTANCE = new WorldEntitySpawnEventBridgeBehaviour();
    private static final EventFactoryLifecycleSystem EVENT_FACTORY_LIFECYCLE_SYSTEM =
            EventFactoryLifecycleSystem.getInstance();

    private WorldEntitySpawnEventBridgeBehaviour() {
    }

    public static WorldEntitySpawnEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelSpawn(Entity entity, CreatureSpawnEvent.SpawnReason spawnReason) {
        if (entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
            CreatureSpawnEvent event = EVENT_FACTORY_LIFECYCLE_SYSTEM.callCreatureSpawnEvent(
                    (EntityLiving) entity, spawnReason
            );
            return event.isCancelled();
        }

        if (entity instanceof EntityItem) {
            ItemSpawnEvent event = EVENT_FACTORY_LIFECYCLE_SYSTEM.callItemSpawnEvent((EntityItem) entity);
            return event.isCancelled();
        }

        return false;
    }
}
