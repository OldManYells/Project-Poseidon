package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.entity.CreatureType;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

/**
 * Canonical behaviour for CraftEventFactory creature-spawn event construction.
 */
public final class CreatureSpawnEventConstructionBehaviour {
    private static final CreatureSpawnEventConstructionBehaviour INSTANCE =
            new CreatureSpawnEventConstructionBehaviour();

    private CreatureSpawnEventConstructionBehaviour() {
    }

    public static CreatureSpawnEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public CreatureSpawnEvent createCreatureSpawnEvent(org.bukkit.entity.Entity entity, CreatureType type,
                                                       SpawnReason reason) {
        return new CreatureSpawnEvent(entity, type, entity.getLocation(), reason);
    }
}
