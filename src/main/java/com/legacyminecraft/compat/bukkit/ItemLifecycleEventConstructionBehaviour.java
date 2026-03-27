package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory item lifecycle event construction.
 */
public final class ItemLifecycleEventConstructionBehaviour {
    private static final ItemLifecycleEventConstructionBehaviour INSTANCE = new ItemLifecycleEventConstructionBehaviour();

    private ItemLifecycleEventConstructionBehaviour() {
    }

    public static ItemLifecycleEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemSpawnEvent createItemSpawnEvent(com.legacyminecraft.compat.bukkit.Entity entity) {
        return new ItemSpawnEvent(entity, entity.getLocation());
    }

    public ItemDespawnEvent createItemDespawnEvent(com.legacyminecraft.compat.bukkit.Entity entity) {
        return new ItemDespawnEvent(entity, entity.getLocation());
    }
}
