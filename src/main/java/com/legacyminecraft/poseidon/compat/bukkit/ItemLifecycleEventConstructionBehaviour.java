package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;

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

    public ItemSpawnEvent createItemSpawnEvent(org.bukkit.entity.Entity entity) {
        return new ItemSpawnEvent(entity, entity.getLocation());
    }

    public ItemDespawnEvent createItemDespawnEvent(org.bukkit.entity.Entity entity) {
        return new ItemDespawnEvent(entity, entity.getLocation());
    }
}
