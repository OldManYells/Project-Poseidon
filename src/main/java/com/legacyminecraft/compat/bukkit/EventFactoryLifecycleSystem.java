package com.legacyminecraft.compat.bukkit;


/**
 * Canonical orchestration system for CraftEventFactory lifecycle-style event flows.
 */
public final class EventFactoryLifecycleSystem {
    private static final EventFactoryLifecycleSystem INSTANCE = new EventFactoryLifecycleSystem();

    private static final EventDispatchBridgeBehaviour EVENT_DISPATCH_BRIDGE_BEHAVIOUR =
            EventDispatchBridgeBehaviour.getInstance();
    private static final EventFactoryContextBridgeBehaviour EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR =
            EventFactoryContextBridgeBehaviour.getInstance();
    private static final CreatureSpawnTypeResolveBehaviour CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR =
            CreatureSpawnTypeResolveBehaviour.getInstance();
    private static final CreatureSpawnEventConstructionBehaviour CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR =
            CreatureSpawnEventConstructionBehaviour.getInstance();
    private static final EntityTameEventConstructionBehaviour ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR =
            EntityTameEventConstructionBehaviour.getInstance();
    private static final ItemLifecycleEventConstructionBehaviour ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR =
            ItemLifecycleEventConstructionBehaviour.getInstance();
    private static final BlockFadeEventConstructionBehaviour BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockFadeEventConstructionBehaviour.getInstance();

    private EventFactoryLifecycleSystem() {
    }

    public static EventFactoryLifecycleSystem getInstance() {
        return INSTANCE;
    }

    public CreatureSpawnEvent callCreatureSpawnEvent(
            EntityLiving entityLiving,
            CreatureSpawnEvent.SpawnReason spawnReason
    ) {
        Entity entity = entityLiving.getBukkitEntity();
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(entity);
        CreatureType type = CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR.resolve(entityLiving);

        CreatureSpawnEvent event = CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR.createCreatureSpawnEvent(
                entity, type, spawnReason);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    public EntityTameEvent callEntityTameEvent(EntityLiving entity, EntityHuman tamer) {
        Entity bukkitEntity = entity.getBukkitEntity();
        AnimalTamer bukkitTamer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveAnimalTamer(tamer);
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(bukkitEntity);

        EntityTameEvent event = ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR.createEntityTameEvent(
                bukkitEntity, bukkitTamer);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    public ItemSpawnEvent callItemSpawnEvent(EntityItem entityItem) {
        Entity entity = entityItem.getBukkitEntity();
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(entity);

        ItemSpawnEvent event = ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemSpawnEvent(entity);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }

    public BlockFadeEvent callBlockFadeEvent(Block block, int type) {
        BlockFadeEvent event = BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockFadeEvent(block, type);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchGlobal(event);
        return event;
    }

    public ItemDespawnEvent callItemDespawnEvent(EntityItem entityItem) {
        Entity entity = entityItem.getBukkitEntity();
        CraftServer craftServer = EVENT_FACTORY_CONTEXT_BRIDGE_BEHAVIOUR.resolveCraftServer(entity);

        ItemDespawnEvent event = ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemDespawnEvent(entity);
        EVENT_DISPATCH_BRIDGE_BEHAVIOUR.dispatchViaServer(craftServer, event);
        return event;
    }
}
